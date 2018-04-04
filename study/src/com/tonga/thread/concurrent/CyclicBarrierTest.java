package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1、一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。
 * 因为该 barrier 在释放等待线程后可以重用，所以称它为循环 的 barrier。
 * 
 * 2、CyclicBarrier 支持一个可选的 Runnable 命令，在一组线程中的最后一个线程到达之后（但在释放所有线程之前），
 * 该命令只在每个屏障点运行一次。若在继续所有参与线程之前更新共享状态，此屏障操作很有用。
 * 
 * 3、另外，CyclicBarrier是可以重用的，它可以在使用完后继续使用，这就是Cyclic（循环）的意思。
 * 
 * 应用场景
 * CyclicBarrier 表示大家彼此等待，大家集合好后才开始出发，分散活动后又在i指定地点集合碰面，
 * 这就好比整个公司的人员利用周末时间集体郊游一样，先各自从家出发到公司集合后，再同时出发到公园游玩，
 * 在指定地点集合后再同时开始就餐。
 *  
 * @Title: CyclicBarrierTest
 * @author tangjia
 * @date 2018-3-4 下午3:25:24
 */
public class CyclicBarrierTest {
    public static void main(String[] args){
        ExecutorService pool = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        
        for (int i = 0; i < 3; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run(){
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"到达地点一，当前等待人数为"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"继续出发":"继续等待"));
                    try {
                        cyclicBarrier.await();//障碍等待点
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"到达地点二，当前等待人数为"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"继续出发":"继续等待"));
                    try {
                        cyclicBarrier.await();//障碍等待点
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"到达地点三，当前等待人数为"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"人齐了出发":"继续等待"));
                    try {
                        cyclicBarrier.await();//障碍等待点
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            pool.execute(runnable);
        }
        pool.shutdown();
    }
}