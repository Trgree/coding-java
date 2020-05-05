package org.ace.example;

import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Liangsj on 2018/2/28.
 */
public class ForeachTest {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4);
        list.forEach(n -> System.out.println(n));

        System.out.println("=============");

        list.forEach(n -> {int m = n +3;
            System.out.println(m);});

        System.out.println("=============");

        // 使用Java 8的方法引用更方便，方法引用由::双冒号操作符标示，
        // 看起来像C++的作用域解析运算符
        list.forEach(System.out :: println);

    }
}
