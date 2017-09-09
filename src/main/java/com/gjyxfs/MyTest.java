package com.gjyxfs;


import java.util.concurrent.atomic.AtomicInteger;

public class MyTest{


    public static void main(String[] args) throws Exception {
        //同时启动1000个线程，去进行i++计算，看看实际结果
//        for (int i = 0; i < 1000; i++) {
//            Thread t = new Thread(new Runnable() {
//                public void run() {
//                    MyTest.inc();
//                }
//            });
//            t.setName("thread"+i);
//            t.start();
//        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" +String.valueOf( 2<<3) );
    }

}
