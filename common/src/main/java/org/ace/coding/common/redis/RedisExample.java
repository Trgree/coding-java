package org.ace.coding.common.redis;


import org.ace.coding.common.utils.PropertiesUtil;
import redis.clients.jedis.JedisCluster;

public class RedisExample {

	public static void main(String[] args) throws Exception {
		PropertiesUtil propUtil = new PropertiesUtil("config/redis.properties");
		JedisClusterUtil factory = new JedisClusterUtil(propUtil);
		JedisCluster jedisCluster = factory.getCluster();
		jedisCluster.set("a", "b");
		jedisCluster.expire("a", propUtil.getInt("redis.key.timeout.second", 60));
		jedisCluster.get("a");
	}
}
