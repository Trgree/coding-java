package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Liangsj on 2018/2/28.
 */
public class PredicateTest {
    public static void main(String[] args) {

        //ava 8也添加了一个包，叫做 java.util.function。它包含了很多类，用来支持Java的函数式编程。
        // 其中一个便是Predicate，使用 java.util.function.Predicate 函数式接口以及lambda表达式，
        // 可以向API方法添加逻辑，用更少的代码支持更多的动态行为
        List<Integer> list = Arrays.asList(1,2,3,4);
        filter(list, (n) -> true);// 所有都打印

        filter(list, (n) -> false);// 所有都不打印

        filter(list, (n) -> (Integer)n==2);// 过滤
    }

    private static void filter(List<Integer> list, Predicate predicate){
        for(Integer i : list){
            if(predicate.test(i)){
                System.out.println("p:" + i);
            }
        }

    }

     private static void filter2(List<Integer> list, Predicate predicate){
         list.forEach(i -> {if(predicate.test(i)){
             System.out.println("p:" + i);
         }});
     }

    // 更好的办法
    public static void filter3(List names, Predicate condition) {
        names.stream().filter((name) -> (condition.test(name))).forEach(n -> {
            System.out.println(n + " ");
        });
    }
}
