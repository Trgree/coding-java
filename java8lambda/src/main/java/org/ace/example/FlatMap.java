package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author L
 * @date 2018/3/10
 */
public class FlatMap {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a,b","c,d");
        list.stream()
                //一维变二维
                .map(n -> n.split(","))
                //二维变维一
                .flatMap(arr -> Arrays.stream(arr))
                .forEach(System.out::println);



        List<String> list2 = Arrays.asList("a,b","c,d");
        list2.stream().flatMap(value -> Stream.of(value.split(","))).forEach(System.out::println);


    }
}
