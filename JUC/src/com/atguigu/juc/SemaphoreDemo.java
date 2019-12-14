package com.atguigu.juc;

import java.util.concurrent.Semaphore;

/**
 * @author wststart
 * @create 2019-07-12 20:16
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5, true);
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println(Thread.currentThread().getName() + "开始用餐");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() +"用餐结束");
                    semaphore.release();
                }
            }, i + "号顾客").start();

        }
    }
}
