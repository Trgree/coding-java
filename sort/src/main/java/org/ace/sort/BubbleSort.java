package org.ace.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * 比较交换相邻元素,每次最大的漂移到最后
 * 时间复杂度是O(n2)
 * Created by Liangsj on 2018/4/12.
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] data = {4,1,2,6,2,3};
        sort(data);
        Arrays.stream(data).forEach(System.out::println);
    }

    public static void sort(int[] data){
        for (int i = 0; i < data.length; i++) {
            for (int j = i; j < data.length - 1; j++) {
                if(data[j] > data[j+1]){
                    int tmp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = tmp;
                }
            }
        }
    }
}
