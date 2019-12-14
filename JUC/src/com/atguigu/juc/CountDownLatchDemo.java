package com.atguigu.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @author wststart
 * @create 2019-07-12 19:02
 */

/**
 * CountDownLatchDemo典型运用场景如促销活动
 * 案例锁门
 */
public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(10);
        System.out.println("班长开始锁门");
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread().getName() + "离开教室");
                    countDownLatch.countDown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, i + "号同学").start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("班长锁门完成");
    }
}
