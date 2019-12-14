package com.atguigu.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author wststart
 * @create 2019-07-12 18:33
 */

/**
 * cyclicBarrier典型运用场景如军训和灭霸响指
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(10, () -> {
            System.out.println("灭霸集齐六颗宝石,响指毁灭宇宙，啪");
        });
        for (int i = 1; i <= 5; i++) {
            new Thread(()->{
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "已收集");
                try {
//                    将线程用await方法阻拦在栅栏外，直到到达栅栏cyclicBarrier的线程数量到达栅栏处才会执行cyclicBarrier线程内的代码
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },i + "号宝石").start();
        }

    }
}
