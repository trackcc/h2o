/**
 * @author 张建伟
 */

import com.jenkov.db.itf.IDaos;
import com.jenkov.db.itf.IResultSetProcessor;
import com.jenkov.db.itf.PersistenceException;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.dao.DbUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class BasedDbLock implements Lock {

    private static final int LOCK = 1;
    private static final int UNLOCK = 0;

    private static final int DEFAULT_SLEEP_TIME = 2;


    private final String lockName;
    private final int sleepTime;

    public BasedDbLock(String lockName) {
        this.lockName = lockName;
        this.sleepTime = DEFAULT_SLEEP_TIME;
    }

    public BasedDbLock(String lockName, int sleepSeconds) {
        this.lockName = lockName;
        this.sleepTime = sleepSeconds;
    }

    public BasedDbLock(String namespace, String lockName) {
        this.lockName = namespace + "_" + lockName;
        this.sleepTime = DEFAULT_SLEEP_TIME;
    }

    public BasedDbLock(String namespace, String lockName, int sleepSeconds) {
        this.lockName = namespace + "_" + lockName;
        this.sleepTime = sleepSeconds;
    }

    public void lock() {
        while (!dbLock()) {

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
            }

        }

    }

    public void lockInterruptibly() throws InterruptedException {

        while (!dbLock()) {
            TimeUnit.SECONDS.sleep(this.sleepTime);
        }

    }

    public boolean tryLock() {
        return dbLock();
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        long mt = unit.toMillis(time);
        long s = System.currentTimeMillis();

        while (!dbLock()) {
            if (System.currentTimeMillis() - s > mt) {
                return false;
            }
            TimeUnit.SECONDS.sleep(this.sleepTime);
        }

        return true;

    }

    public void unlock() {
        dbUnLock();
    }

    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }


    private volatile long version = 0;

    private boolean dbLock() {

        Tuple2<Long, Integer> lockStatus = getLockStatus(lockName);

        if (lockStatus.e1 == LOCK) {
            return false;
        }

        long v = lockStatus.e0;

        long nv = v + 1;

        boolean l = updateLockStatus(lockName, v, nv, LOCK);
        if (l) {
            version = nv;
        }

        return l;

    }

    private boolean dbUnLock() {
        return updateLockStatus(lockName, version, version + 1, UNLOCK);
    }


    // ================================================
    // 底层实现
    // ===============================================


    private static final String SEL = DbUtil.sqlTable.getSql("sel");
    private static final String INS = DbUtil.sqlTable.getSql("ins");
    private static final String UPD = DbUtil.sqlTable.getSql("upd");

    public static Tuple2<Long, Integer> getLockStatus(String key) {

        Tuple2<Long, Integer> lockStatus = DbUtil.getButterflyDb("common").getDao(true).read(SEL, new IResultSetProcessor() {

            private Tuple2<Long, Integer> lockStatus;

            public void init(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {
                if (rs.next()) {
                    lockStatus = TupleUtil.t(rs.getLong(1), rs.getInt(2));
                } else {
                    lockStatus = null;
                }
            }

            public void process(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {

            }

            public Object getResult() throws PersistenceException {
                return lockStatus;
            }

        }, key);

        if (lockStatus == null) {
            try {
                int urow = DbUtil.getButterflyDb("common").getDao(true).update(INS, key, 1L, 0);
                if (urow > 0) {
                    lockStatus = TupleUtil.t(1L, 0);
                }
            } catch (Exception e) {
            }
        }

        return lockStatus;

    }

    private static boolean updateLockStatus(final String key, long v, long nv, final int lock) {

        return DbUtil.getButterflyDb("common").getDao(true).update(UPD, nv, lock, key, v) > 0;

    }


}
