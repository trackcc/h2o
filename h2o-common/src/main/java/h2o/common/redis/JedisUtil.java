package h2o.common.redis;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.collections.tuple.TupleUtil;
import h2o.common.util.lang.StringUtil;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

public class JedisUtil {

	protected final RedisConfig[] redisConfigs;

	private volatile int ji = -1;

	
	@SuppressWarnings("unchecked")
	public JedisUtil( String... confs ) {
		
		redisConfigs = new RedisConfig[confs.length];
		
		int i = 0;
		for( String conf : confs ) {

		    String pass = null;
		    if( StringUtils.contains( conf , '@' ) ) {
                pass = StringUtils.substringAfter( conf , "@");
                conf = StringUtils.substringBefore( conf , "@");
            }
			
			String host = StringUtils.substringBefore( conf , ":");
			String p = StringUtils.substringAfter( conf , ":");
			Integer port = StringUtils.isBlank(p) ? 6379 : new Integer(p);
			
			redisConfigs[i++] = new RedisConfig( host , port , pass );
		}

	}


    public JedisUtil( RedisConfig... confs ) {
        redisConfigs = confs;
    }


	public Jedis getJedis() {
		int i = ji;
		Jedis j = create(i);
		if( check(j) ) {
			return j;
		} else {
			return select();
		}
		
	}


    protected Jedis create( int i ) {
		if ( i == -1 ) {
		    return null;
        }

        try {

		    RedisConfig conf = redisConfigs[i];

            Jedis jedis = conf.timeout == null ?
                        new Jedis(conf.host, conf.port) :
                        new Jedis(conf.host, conf.port , conf.timeout);


            if ( conf.pass != null ) {
                jedis.auth(conf.pass);
            }

            return jedis;


        } catch ( Exception e ) {

		    Tools.log.error( e );

		    return null;

        }

	}

	protected boolean check(Jedis jedis) {

		if (jedis == null) {
			return false;
		}

		try {
			return "PONG".equals(jedis.ping());
		} catch (Exception e) {
			Tools.log.debug("Check Jedis[{}] error!", jedis);		
			close(jedis);
		}

		return false;

	}

	private Jedis select() {		
		for (int i = 0; i < redisConfigs.length; i++) {
			Jedis j = create(i);
			if (check(j)) {
				ji = i;
				return j;
			}
		}
		ji = -1;
		return null;
	}


	public static void close( Jedis jedis ) {
		if( jedis != null ) try {
			jedis.close();
		} catch (Exception e) {
			Tools.log.error("Close Jedis[" + jedis + "] error!", e );
		}
	}
	
	public <T> T callback( JedisCallBack<T> jedisCallBack) {
		return callback(jedisCallBack,false);
	}
	
	public <T> T callback( JedisCallBack<T> jedisCallBack , boolean isSilently ) {
		
		Jedis jedis = getJedis();
		if( jedis == null ) {
			if(isSilently) {
				return null;
			} else {
				throw new RuntimeException("Not available jedis!");
			}
		}
		
		try {
			return jedisCallBack.doCallBack(jedis);
		} catch( Exception e ) {
			throw ExceptionUtil.toRuntimeException(e);
		} finally {
			close(jedis);
		}
		
		
	}
	

}
