package org.ace.sort;

import java.util.Arrays;

/**
 * 选择排序
 * 每轮选择最小的放到前面.
 * Created by Liangsj on 2018/4/12.
 */
public class SelectSort {


    public static void main(String[] args) {
        int[] data = {4,1,2,6,2,3};
        sort(data);
        Arrays.stream(data).forEach(System.out::println);
    }

    public static void sort(int[] data){
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if(data[i] > data[j]){
                    int tmp = data[i];
                    data[i] = data[j];
                    data[j] = tmp;
                }
            }
        }
    }
}
