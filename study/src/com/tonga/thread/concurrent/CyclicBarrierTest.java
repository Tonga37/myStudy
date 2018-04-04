package com.tonga.thread.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1��һ��ͬ�������࣬������һ���̻߳���ȴ���ֱ������ĳ���������ϵ� (common barrier point)��
 * ���漰һ��̶���С���̵߳ĳ����У���Щ�̱߳��벻ʱ�ػ���ȴ�����ʱ CyclicBarrier �����á�
 * ��Ϊ�� barrier ���ͷŵȴ��̺߳�������ã����Գ���Ϊѭ�� �� barrier��
 * 
 * 2��CyclicBarrier ֧��һ����ѡ�� Runnable �����һ���߳��е����һ���̵߳���֮�󣨵����ͷ������߳�֮ǰ����
 * ������ֻ��ÿ�����ϵ�����һ�Ρ����ڼ������в����߳�֮ǰ���¹���״̬�������ϲ��������á�
 * 
 * 3�����⣬CyclicBarrier�ǿ������õģ���������ʹ��������ʹ�ã������Cyclic��ѭ��������˼��
 * 
 * Ӧ�ó���
 * CyclicBarrier ��ʾ��ұ˴˵ȴ�����Ҽ��Ϻú�ſ�ʼ��������ɢ�������iָ���ص㼯�����棬
 * ��ͺñ�������˾����Ա������ĩʱ�伯�彼��һ�����ȸ��ԴӼҳ�������˾���Ϻ���ͬʱ��������԰���棬
 * ��ָ���ص㼯�Ϻ���ͬʱ��ʼ�Ͳ͡�
 *  
 * @Title: CyclicBarrierTest
 * @author tangjia
 * @date 2018-3-4 ����3:25:24
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
                    System.out.println(Thread.currentThread().getName()+"����ص�һ����ǰ�ȴ�����Ϊ"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"��������":"�����ȴ�"));
                    try {
                        cyclicBarrier.await();//�ϰ��ȴ���
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
                    System.out.println(Thread.currentThread().getName()+"����ص������ǰ�ȴ�����Ϊ"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"��������":"�����ȴ�"));
                    try {
                        cyclicBarrier.await();//�ϰ��ȴ���
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
                    System.out.println(Thread.currentThread().getName()+"����ص�������ǰ�ȴ�����Ϊ"+(cyclicBarrier.getNumberWaiting()+1)+(cyclicBarrier.getNumberWaiting()+1==3?"�����˳���":"�����ȴ�"));
                    try {
                        cyclicBarrier.await();//�ϰ��ȴ���
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