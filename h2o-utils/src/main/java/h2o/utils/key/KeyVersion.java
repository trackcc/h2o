package h2o.utils.key;


import com.jenkov.db.itf.IDaos;
import com.jenkov.db.itf.IResultSetProcessor;
import com.jenkov.db.itf.PersistenceException;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.util.dao.butterflydb.ButterflyDao;
import h2o.common.util.dao.butterflydb.ButterflyDb;
import h2o.common.util.dao.butterflydb.ButterflyDbCallback;
import h2o.dao.DbUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;


public class KeyVersion {

    private static final int RETRYTIMES = 100;

    private static final String SELSEQ = DbUtil.sqlTable.getSql("selSeq");
    private static final String INSSEQ = DbUtil.sqlTable.getSql("insSeq");
    private static final String UPDSEQ = DbUtil.sqlTable.getSql("updSeq");

    private static String makeKey(String key) {
        return key;
    }

    public static long incVersion(String key) {
        key = makeKey(key);

        Long k = Long.valueOf(-1L);
        int n = 0;
        while (((k = incAndUpdateKey(key)).longValue() == -1L) && (n++ < RETRYTIMES))
            try {
                TimeUnit.MILLISECONDS.sleep(20L);
            } catch (InterruptedException localInterruptedException) {
            }
        if (k.longValue() == -1L) {
            throw new RuntimeException("incVersion 失败!!!");
        }

        return k.longValue();
    }

    public static long getVersion(String key) {
        key = makeKey(key);

        return (Long) DbUtil.getButterflyDb("common").getDao(true).read(SELSEQ, new IResultSetProcessor() {

            private Long key;

            public void init(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {
                if (rs.next()) {
                    key = rs.getLong(1);
                } else {
                    key = 0L;
                }
            }

            public void process(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {

            }

            public Object getResult() throws PersistenceException {
                return key;
            }

        }, key);

    }

    private static Long incAndUpdateKey(final String key) {

        final long[] rr = {-1};


        DbUtil.getButterflyDb("common").txCallback(new ButterflyDbCallback<String>() {

            public String doCallBack(ButterflyDb butterflyDb, Connection connection) throws Exception {

                final ButterflyDao jdbcDao = butterflyDb.getDao();

                @SuppressWarnings("unchecked")
                Tuple2<Long, Boolean> qr = (Tuple2<Long, Boolean>) jdbcDao.read(SELSEQ, new IResultSetProcessor() {

                    private Tuple2<Long, Boolean> qr;

                    public void init(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {
                        if (rs.next()) {
                            qr = TupleUtil.t(rs.getLong(1), false);
                        } else {
                            qr = TupleUtil.t(0L, true);
                        }
                    }

                    public void process(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {

                    }

                    public Object getResult() throws PersistenceException {
                        return qr;
                    }

                }, key);

                int unum = 0;

                long r = qr.e0 + 1;

                if (qr.e1) {
                    try {
                        unum = jdbcDao.update(INSSEQ, key, r);
                    } catch (Exception e) {
                        unum = 0;
                    }
                } else {
                    unum = jdbcDao.update(UPDSEQ, r, key, qr.e0);
                }

                rr[0] = (unum == 0 ? -1 : r);
                return null;
            }
        });
        return rr[0];
    }
}