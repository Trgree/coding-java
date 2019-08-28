package org.ace.coding.distinct.hll;

import org.ace.coding.common.redis.JedisClusterTool;
import org.ace.coding.common.utils.PropsTool;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 基于redis的基数统计
 * java -cp xxx.jar  org.ace.coding.distinct.hll.RedisHLLExample  redis.properties 1000000
 * Created by LiangShujie
 * Date: 2019/8/27 14:38
 */
public class RedisHLLExample {
    public static void main(String[] args) throws Exception {
        String config = "redis.properties";
        long size = 100000;
        if(args.length > 1){
            config = args[0];
            size = Long.parseLong(args[1]);
        }
        JedisClusterTool jedis = new JedisClusterTool(new PropsTool(config));
        JedisCluster cluster = jedis.getCluster();
        Set<Integer> set = new HashSet<>();
        Random ran = new Random();
        cluster.del("cardinality");
        long start = System.currentTimeMillis();
        for (int i=0;i<size;i++) {
            int d = ran.nextInt((int)size);
            cluster.pfadd("cardinality", d+"");
            set.add(d);
        }
        System.out.println("总数：" + size);
        System.out.println("distinct数：" + set.size());
        System.out.println("redis计算出的基数:" + cluster.pfcount("cardinality"));
        System.out.println("错误率：" + (double)(cluster.pfcount("cardinality")-set.size())/set.size() * 100 + "%");
        System.out.println("用时：" + ((System.currentTimeMillis() - start)/1000) + "s");
        cluster.del("cardinality");
        cluster.close();
        /*
        总数：1000000
        distinct数：632424
        redis计算出的基数:629833
        错误率：-0.4096934967679911%
        用时：183s
         */
        /*
        线上：
         总数：1000000
        distinct数：631911
        redis计算出的基数:625197
        错误率：-1.0624913951490005%
        用时：169s
         */
    }
}
