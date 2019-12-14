package com.atguigu.juc;

/**
 * @author wststart
 * @create 2019-07-12 19:18
 */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 利用lock来实现线程通信,并按照顺序唤醒制定线程,使用wait和notify的组合不能实现此功能
 */
class Restaurant {
    private ReentrantLock lock = new ReentrantLock();
    private Condition cutcondition = lock.newCondition();
    private Condition cookcondition = lock.newCondition();
    private Condition passcondition = lock.newCondition();
    private Status status = Status.CUT;

    //切菜方法

    public void cut() {
        lock.lock();
        try {
            while (!status.equals(Status.CUT)) {
                try {
                    cutcondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "切菜完成");
            status = Status.COOK;
            cookcondition.signal();
        } finally {
            lock.unlock();

        }
    }

    public void cook() {
        lock.lock();
        try {
            while (!status.equals(Status.COOK)) {
                try {
                    cookcondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "炒菜完成");
            status = Status.PASS;
            passcondition.signal();
        } finally {
            lock.unlock();

        }
    }

    public void pass() {
        lock.lock();
        try {
            while (!status.equals(Status.PASS)) {
                try {
                    passcondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + "送菜完成");
            status = Status.CUT;
            cutcondition.signal();
        } finally {
            lock.unlock();

        }
    }

}

public class ConditionDemo {
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                restaurant.cut();
            }
        }, "切菜工廖某").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                restaurant.cook();
            }
        }, "炒菜工小廖").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                restaurant.pass();
            }
        }, "送菜工廖彧惠").start();
    }
}
