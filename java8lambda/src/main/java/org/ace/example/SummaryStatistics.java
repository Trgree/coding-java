package org.ace.example;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;

/**
 * Created by Liangsj on 2018/3/1.
 */
public class SummaryStatistics {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4);

        //IntStream、LongStream 和 DoubleStream 等流的类中，有个非常有用的方法叫做 summaryStatistics() 。
        // 可以返回 IntSummaryStatistics、LongSummaryStatistics 或者 DoubleSummaryStatistic s，
        // 描述流中元素的各种摘要数据
        IntSummaryStatistics intSummaryStatistics = list.stream().mapToInt(i -> i).summaryStatistics();
        System.out.println(intSummaryStatistics.getAverage());
        System.out.println(intSummaryStatistics.getCount());
        System.out.println(intSummaryStatistics.getMax());

    }
}
