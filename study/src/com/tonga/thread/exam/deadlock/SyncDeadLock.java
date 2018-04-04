package com.tonga.thread.exam.deadlock;

/**
 * 
 * <p>Title: SyncDeadLock.java</p>  
 * @author tangjia
 * @date 2018-3-14 ÏÂÎç5:59:26 
 * @version 1.0
 */
public class SyncDeadLock{
    private static Object locka = new Object();
    private static Object lockb = new Object();

    public static void main(String[] args){
        new SyncDeadLock().deadLock();
    }

    private void deadLock(){
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (locka){
                    try{
                        System.out.println(Thread.currentThread().getName()+" get locka ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need lockb!Just waiting!");
                    synchronized (lockb){
                        System.out.println(Thread.currentThread().getName()+" get lockb ing!");
                    }
                }
            }
        },"thread1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lockb){
                    try{
                        System.out.println(Thread.currentThread().getName()+" get lockb ing!");
                        Thread.sleep(500);
                        System.out.println(Thread.currentThread().getName()+" after sleep 500ms!");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+" need locka! Just waiting!");
                    synchronized (locka){
                        System.out.println(Thread.currentThread().getName()+" get locka ing!");
                    }
                }
            }
        },"thread2");

        thread1.start();
        thread2.start();
    }
}