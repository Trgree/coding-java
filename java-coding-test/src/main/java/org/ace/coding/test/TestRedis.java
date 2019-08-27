package org.ace.coding.test;

import org.ace.coding.common.redis.JedisClusterUtil;
import org.ace.coding.common.utils.PropsTool;

/**
 * Created by LiangShujie
 * Date: 2019/8/27 13:37
 */
public class TestRedis {
    public static void main(String[] args) throws Exception {
        PropsTool propsTool = new PropsTool("redis.properties");
        JedisClusterUtil util = new JedisClusterUtil(propsTool);
        System.out.println(util.getCluster().get("a"));
    }
}
