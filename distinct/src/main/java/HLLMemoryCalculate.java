import com.clearspring.analytics.stream.cardinality.AdaptiveCounting;
import com.clearspring.analytics.stream.cardinality.ICardinality;

import java.util.Random;

/**
 * 计算基数统计占用内存
 * java -cp xxx.jar -Xms10M -Xmx10M  org.ace.coding.distinct.hll.HLLMemoryCalculate  100000000
 * 1亿的数据，占用内存不到10M
 * Created by LiangShujie
 * Date: 2019/8/27 14:06
 */
public class HLLMemoryCalculate {
    public static void main(String[] args) {
        long size = 10000000;
        if(args.length > 0){
            size = Long.parseLong(args[0]);
        }
        Runtime rt = Runtime.getRuntime();
        ICardinality card = AdaptiveCounting.Builder.obyCount(Integer.MAX_VALUE).build();
        Random ran = new Random();
        long start = System.currentTimeMillis();
        for (int i=0;i<size;i++) {
            int d = ran.nextInt((int)size);
            card.offer(d);
        }

        System.out.println("Total count:[" + size + "]  Unique count:[" + card.cardinality() + "] TotalMemory:[" + rt.totalMemory()/1024/1024 + "MB] "+ "] FreeMemory:[" + rt.freeMemory()/1024/1024 + "MB] ");
        System.out.println("用时：" + ((System.currentTimeMillis() - start)) + "ms");
    }
}
