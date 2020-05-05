package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Liangsj on 2018/3/1.
 */
public class MRTest {
    public static void main(String[] args) {
        // 不使用lambda表达式为每个订单加上12%的税
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }

        int a = 4;
        // map
        costBeforeTax.stream().map(n -> a + n + .12*n).forEach(System.out :: println);

        // reduce
        Double total = costBeforeTax.stream().map(n -> n + .12*n).reduce((sum, cost) -> sum + cost).get();
        System.out.println("total:" + total);



    }
}
