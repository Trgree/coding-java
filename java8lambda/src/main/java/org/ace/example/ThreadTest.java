package org.ace.example;

/**
 * Created by Liangsj on 2018/2/28.
 */
public class ThreadTest {
    public static void main(String[] args) {

        // 线程  Java 8之前：
        new Thread(new Runnable() {
            public void run() {
                System.out.println("Runnable ...");
            }
        }).start();

        // lambda  Java 8之后：
        new Thread(() -> System.out.println("lambda Runnable ... ")).start();
    }
}
