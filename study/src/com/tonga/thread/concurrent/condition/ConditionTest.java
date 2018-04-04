package com.tonga.thread.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition的执行方式，是当在线程1中调用await方法后，线程1将释放锁，并且将自己沉睡，等待唤醒，线程2获取到锁后，开始做事，完毕后，调用Condition的signal方法，唤醒线程1，线程1恢复执行。
 * 以上说明Condition是一个多线程间协调通信的工具类，使得某个，或者某些线程一起等待某个条件（Condition）,只有当该条件具备( signal 或者 signalAll方法被带调用)时 ，这些等待线程才会被唤醒，从而重新争夺锁。
 * Condition与传统线程通信有些类似，它的使用更广，可以将多个线程进行通信，以完成更加复杂的通信。
 * 
 * 在Condition中，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()，传统线程的通信方式，Condition都可以实现，
 * 这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
 * 
 * Condition的强大之处在于它可以为多个线程间建立不同的Condition
 * 
 * @Title: ConditionTest
 * @author tangjia
 * @date 2018-3-4 下午4:04:19
 */
public class ConditionTest {
    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("我需要等一个信号"+this);
                    condition.await();
                    System.out.println("我拿到一个信号"+this);
                } catch (Exception e) {
                    // TODO: handle exception
                } finally{
                    lock.unlock();
                }
                
                
            }
        }, "thread1");
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("我拿到了锁");
                    Thread.sleep(500);
                    System.out.println("我发出一个信号");
                    condition.signal();
                } catch (Exception e) {
                    // TODO: handle exception
                } finally{
                    lock.unlock();
                }
                
                
            }
        }, "thread2");
        thread2.start();
    }
}