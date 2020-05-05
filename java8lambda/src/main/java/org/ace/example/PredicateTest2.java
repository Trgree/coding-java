package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Liangsj on 2018/3/1.
 */
public class PredicateTest2 {
    public static void main(String[] args) {
        //Predicate 允许将两个或更多的 Predicate 合成一个。
        // 它提供类似于逻辑操作符AND和OR的方法，名字叫做and()、or()和xor()，
        // 用于将传入 filter() 方法的条件合并起来
        List<String> list = Arrays.asList("hello", "world", "haha");
        Predicate<String> startWithH = (s) -> s.startsWith("h");
        Predicate<String> fourLetterLong = (s) -> s.length() == 4;
        list.stream().filter(startWithH.and(fourLetterLong))
                .forEach((s) -> System.out.println(s));
    }
}
