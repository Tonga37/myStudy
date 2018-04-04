package com.tonga.thread.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @Title: ThreeConditionCommunication
 * @author tangjia
 * @date 2018-3-4 下午3:53:04
 */
public class ThreeConditionCommunication {
    public static void main(String[] args) {
        final Business business = new Business();
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    business.sub1(i);
                }
            }
        }).start();
        new Thread(new Runnable() {
            
            @Override
            public void run() {
                for (int i = 0; i < 3; i++) {
                    business.sub2(i);
                }
            }
        }).start();
        for (int i = 0; i < 3; i++) {
            business.main(i);
        }
    }
    static class Business{
        Lock lock = new ReentrantLock();
        Condition main = lock.newCondition();
        Condition sub1 = lock.newCondition();
        Condition sub2 = lock.newCondition();
        int runNum = 1;
        
        public void main(int i){
            lock.lock();
            try {
                while(runNum!=1){
                    main.await();//主线程等待
                }
                for (int j = 0; j < 1; j++) {
                    System.out.println("main is looping of "+j+" in "+i);
                }
                runNum = 2;
                sub1.signal();//唤醒子线程1
            } catch (Exception e) {
                // TODO: handle exception
            } finally{
                lock.unlock();
            }
        }
        public void sub1(int i){
            lock.lock();
            try {
                while(runNum!=2){
                    sub1.await();//子线程1等待
                }
                for (int j = 0; j < 1; j++) {
                    System.out.println("sub1 is looping of "+j+" in "+i);
                }
                runNum = 3;
                sub2.signal();//唤醒子线程2
            } catch (Exception e) {
                // TODO: handle exception
            } finally{
                lock.unlock();
            }
        }
        public void sub2(int i){
            lock.lock();
            try {
                while(runNum!=3){
                    sub2.await();//子线程2等待
                }
                for (int j = 0; j < 1; j++) {
                    System.out.println("sub2 is looping of "+j+" in "+i);
                }
                runNum = 1;
                main.signal();//唤醒主线程
            } catch (Exception e) {
                // TODO: handle exception
            } finally{
                lock.unlock();
            }
        }
    }
}