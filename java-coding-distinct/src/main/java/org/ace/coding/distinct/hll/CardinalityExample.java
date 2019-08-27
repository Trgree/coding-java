package org.ace.coding.distinct.hll;

import com.clearspring.analytics.stream.cardinality.AdaptiveCounting;
import com.clearspring.analytics.stream.cardinality.ICardinality;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * 基于内存的基数统计
 * Created by LiangShujie
 * Date: 2019/8/27 14:01
 */
public class CardinalityExample {
    public static void main(String[] args) {
        // 构建Cardinality
        ICardinality card = AdaptiveCounting.Builder.obyCount(Integer.MAX_VALUE).build();
        // set,用于对比，计算错误率
        Set<Integer> set = new HashSet<>();
        Random ran = new Random();

        for (int i=0;i<100000;i++) {
            int d = ran.nextInt(10000000);
            card.offer(d);// 放入数据
            set.add(d);
        }
        System.out.println(card.cardinality());// 去重数
        System.out.println(set.size());
        System.out.println("错误率：" + (double)(card.cardinality()-set.size())/set.size() * 100 + "%");
    }
}
