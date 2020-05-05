package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Liangsj on 2018/3/1.
 */
public class Collector {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "haha");
        List<String> filterList = list.stream().filter(s -> s.length() > 4)
                .collect(Collectors.toList());// 返回List
        String filterString = list.stream().filter(s -> s.length() > 4)
                .collect(Collectors.joining(","));// 返回Sting

        System.out.println(filterList);
        System.out.println(filterString);
    }
}
