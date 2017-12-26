package h2o.common.thirdparty.redis;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

public class JedisUtil {

    private final RedisConfig[] redisConfigs;

	private volatile int ji = -1;

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
		Jedis j = getJedis(i);

		if( check(j) ) {
			return j;
		} else {
			return select();
		}
		
	}

    private Jedis select() {

        for (int i = 0; i < redisConfigs.length; i++) {
            Jedis j = getJedis(i);
            if (check(j)) {
                ji = i;
                return j;
            }
        }

        ji = -1;

        return null;

    }



    private Jedis getJedis( int i ) {

		if ( i == -1 ) {
		    return null;
        }

        try {

            return getJedis(redisConfigs[i]);

        } catch ( Exception e ) {

		    Tools.log.error( e );

		    return null;

        }

	}


	protected Jedis getJedis( RedisConfig conf ) {

        Jedis jedis = conf.timeout == null ?
                new Jedis(conf.host, conf.port) :
                new Jedis(conf.host, conf.port , conf.timeout);


        if ( conf.pass != null ) {
            jedis.auth(conf.pass);
        }

        return jedis;
    }



	protected boolean check( Jedis jedis ) {

		if (jedis == null) {
			return false;
		}

		try {
			return "PONG".equals(jedis.ping());
		} catch (Exception e) {
			Tools.log.debug("Check Jedis[{}] error!", jedis);
            release(jedis);
		}

		return false;

	}



    public void release( Jedis jedis ) {
		close( jedis );
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
            release(jedis);
		}
		
		
	}
	

}
