package org.ace.coding.common.redis;

import org.ace.coding.common.utils.PropsTool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 *  Redis集群连接工具
 *  使用properties配置文件
 */
public class JedisClusterUtil {

	private String addressKeyPrefix = "redis.address";
	private JedisCluster jedisCluster;
	private Pattern p = Pattern.compile("^.+[:]\\d{1,5}\\s*$");
	private PropsTool propTool;
	private GenericObjectPoolConfig conf = new GenericObjectPoolConfig();

	public JedisClusterUtil(PropsTool propUtil) throws Exception {
		this.propTool = propUtil;
		parseHostAndPort();
		propertiesSet();
		afterPropertiesSet();
	}

	private void propertiesSet() {
		conf.setMinIdle(propTool.getInt("redis.minIdle", 2));
		conf.setMaxIdle(propTool.getInt("redis.maxIdle", 100));
		conf.setMaxTotal(propTool.getInt("redis.maxTotal", 1000));
		conf.setMaxWaitMillis(propTool.getInt("redis.maxWaitMillis", -1));
	}

	public JedisCluster getCluster() {
		return jedisCluster;
	}

	private Set<HostAndPort> parseHostAndPort() throws Exception {
		try {
			Set<HostAndPort> haps = new HashSet<HostAndPort>();
			for (Object key : propTool.getProp().keySet()) {
				if (!((String) key).startsWith(addressKeyPrefix)) {
					continue;
				}
				String val = (String) propTool.getProp().get(key);
				boolean isIpPort = p.matcher(val.trim()).matches();
				if (!isIpPort) {
					throw new IllegalArgumentException("ip 或 port 不合法");
				}
				String[] ipAndPort = val.split(":");
				HostAndPort hap = new HostAndPort(ipAndPort[0], Integer.parseInt(ipAndPort[1]));
				haps.add(hap);
			}
			return haps;
		} catch (IllegalArgumentException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new Exception("解析 jedis 配置文件失败", ex);
		}
	}

	public void afterPropertiesSet() throws Exception {
		Set<HostAndPort> haps = this.parseHostAndPort();
		int connectionTimeout = propTool.getInt("redis.connectionTimeout", 2000);
		int soTimeout = propTool.getInt("redis.soTimeout", 2000);
		int maxAttempts = propTool.getInt("redis.maxAttempts", 5);
		String password = propTool.getString("redis.password").trim();
		if(password == null || password.equals("")){
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxAttempts, conf);
		} else {
			jedisCluster = new JedisCluster(haps, connectionTimeout, soTimeout, maxAttempts, password, conf);
		}
	}
}
