package h2o.common.redis;

import h2o.common.Tools;
import h2o.common.exception.ExceptionUtil;
import h2o.common.util.collections.tuple.Tuple2;
import h2o.common.util.collections.tuple.TupleUtil;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

public class JedisUtil {

	private final Tuple2<String, Integer>[] clients;

	private volatile int ji = -1;

	
	@SuppressWarnings("unchecked")
	public JedisUtil( String... hosts ) {
		
		clients = new Tuple2[hosts.length];
		
		int i = 0;
		for( String host : hosts ) {
			
			String h = StringUtils.substringBefore( host , ":");
			String p = StringUtils.substringAfter( host , ":");
			Integer port = StringUtils.isBlank(p) ? 6379 : new Integer(p);
			
			clients[i++] = TupleUtil.t(h,port);
		}
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
	

	private Jedis create( int i) {		
		return i == -1 ? null : new Jedis(clients[i].e0,clients[i].e1);
	}

	private boolean check(Jedis jedis) {

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
		for (int i = 0; i < clients.length; i++) {			
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
