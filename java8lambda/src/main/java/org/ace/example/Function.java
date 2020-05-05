package org.ace.example;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

/**
 * @author L
 * @date 2018/3/10
 */
public class Function {
    public static void main(String[] args) {
        System.out.println(sum(0, Integer::sum));
    }

    public static Integer sum(int init, BinaryOperator<Integer> function){

        List<Integer> list2 = Arrays.asList(1,2,3,4);

        return list2.stream().reduce(init, function);
    }
}
