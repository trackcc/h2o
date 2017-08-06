package h2o.utils.key;


import com.jenkov.db.itf.IDaos;
import com.jenkov.db.itf.IResultSetProcessor;
import com.jenkov.db.itf.PersistenceException;
import h2o.common.Tools;
import h2o.common.concurrent.LockMap;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.Tuple3;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.util.dao.butterflydb.ButterflyDao;
import h2o.common.util.dao.butterflydb.ButterflyDb;
import h2o.common.util.dao.butterflydb.ButterflyDbCallback;
import h2o.common.util.math.IntArith;
import h2o.dao.DbUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class KeyGen {

    public static final String DEFAULT_CYCLICSPACE = "_null_";

    static final int RETRYTIMES = 100;

    private static final LockMap lockMap = new LockMap();


    private static final String SELSEQ = DbUtil.sqlTable.getSql("selseq");
    private static final String INSSEQ = DbUtil.sqlTable.getSql("insseq");
    private static final String UPDSEQ = DbUtil.sqlTable.getSql("updseq");



    static String leftPad(String sn, int length) {
        return StringUtils.leftPad(sn, length, '0');
    }


    public static String getKey(String seq, int length) {
        return leftPad(getKey(seq), length);
    }

    public static String getKey(String seqObj, String cyclicSpace, int length) {
        return leftPad(getKey( seqObj, cyclicSpace ), length);
    }


    public static String getKey(String seqObj, CyclicSpaceSetter cyclicSpaceSetter , int length) {
        return leftPad(getKey( seqObj, cyclicSpaceSetter ), length);
    }


    public static String getKey(String seq) {
        return getKey(seq, (String)null);
    }


    public static String getKey(String seqObj, String cyclicSpace) {
        cyclicSpace = StringUtils.isBlank(cyclicSpace) ? DEFAULT_CYCLICSPACE : cyclicSpace;
        return getKey( seqObj , new DefaultCyclicSpaceSetter(cyclicSpace) );
    }


    public static String getKey( String seqObj , CyclicSpaceSetter cyclicSpaceSetter ) {

        Lock lock = lockMap.getLock(seqObj);
        lock.lock();
        try {

            String k = null;
            int n = 0;
            while (((k = incAndUpdateKey(seqObj, cyclicSpaceSetter)) == null) && (n++ < RETRYTIMES)) {
                try {
                    TimeUnit.MILLISECONDS.sleep(20L);
                } catch (InterruptedException localInterruptedException) {
                }
            }

            if (k == null) {
                throw new RuntimeException("getKey 失败!!!");
            }

            Tools.log.info("getKey({})============>{}", seqObj, k);

            return k;

        } finally {
            lock.unlock();
        }
    }



    public static BigInteger getNumberKey(String seq) {
        return new BigInteger(getKey(seq));
    }

    public static BigInteger getNumberKey(String seqObj, String cyclicSpace) {
        return new BigInteger(getKey(seqObj, cyclicSpace));
    }

    public static BigInteger getNumberKey(String seqObj, CyclicSpaceSetter cyclicSpaceSetter) {
        return new BigInteger(getKey(seqObj, cyclicSpaceSetter));
    }


    private static String incAndUpdateKey(final String key, final CyclicSpaceSetter cyclicSpaceSetter) {
        Tuple2<String, String> kk = incAndUpdateKey(key, cyclicSpaceSetter, "1");
        return kk == null ? null : kk.e0;
    }


    /**
     * @return 新值,原值
     */
    static Tuple2<String, String> incAndUpdateKey(final String key, final CyclicSpaceSetter cyclicSpaceSetter , final String incNum) {

        final String[] rr = {"-1", "-1"};


        DbUtil.getButterflyDb("common").txCallback(new ButterflyDbCallback<String>() {


            public String doCallBack(ButterflyDb butterflyDb, Connection connection) throws Exception {

                final ButterflyDao jdbcDao = butterflyDb.getDao();

                // 序列号,循环空间 , 记录是否存在
                Tuple3<String, String, Boolean> qr = jdbcDao.read(SELSEQ, new IResultSetProcessor() {

                    private Tuple3<String, String, Boolean> qr;

                    public void init(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {
                        if (rs.next()) {

                            String cycdb = rs.getString(1);
                            String sndb  = rs.getString(2);

                            qr = TupleUtil.t( sndb, cycdb , true);

                        } else {

                            qr = TupleUtil.t("0", null, false);

                        }
                    }

                    public void process(ResultSet rs, IDaos daos) throws SQLException, PersistenceException {

                    }

                    public Object getResult() throws PersistenceException {
                        return qr;
                    }

                }, key );




                String cyclicSpace = cyclicSpaceSetter.getNewCyclicSpace( qr.e1 , qr.e0 );
                if ( cyclicSpace == null ) {
                    cyclicSpace = DEFAULT_CYCLICSPACE;
                }

                String r0 = ( qr.e2 &&  cyclicSpace.equals( qr.e1 ) ) ?  qr.e0 : "0";

                String r = IntArith.add( r0 , incNum );

                int unum = 0;
                if (!qr.e2) {
                    try {
                        unum = jdbcDao.update(INSSEQ, key, cyclicSpace, r);
                    } catch (Exception e) {
                        unum = 0;
                    }
                } else {
                    unum = jdbcDao.update( UPDSEQ, r, cyclicSpace, key, qr.e0 , qr.e1);
                }

                rr[0] = (unum == 0 ? null : r);
                rr[1] = qr.e0;

                return null;

            }

        });

        return rr[0] == null ? null : TupleUtil.t( rr[0], rr[1] );

    }


    


}