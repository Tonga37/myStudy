package com.tonga.thread.concurrent.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition��ִ�з�ʽ���ǵ����߳�1�е���await�������߳�1���ͷ��������ҽ��Լ���˯���ȴ����ѣ��߳�2��ȡ�����󣬿�ʼ���£���Ϻ󣬵���Condition��signal�����������߳�1���߳�1�ָ�ִ�С�
 * ����˵��Condition��һ�����̼߳�Э��ͨ�ŵĹ����࣬ʹ��ĳ��������ĳЩ�߳�һ��ȴ�ĳ��������Condition��,ֻ�е��������߱�( signal ���� signalAll������������)ʱ ����Щ�ȴ��̲߳Żᱻ���ѣ��Ӷ�������������
 * Condition�봫ͳ�߳�ͨ����Щ���ƣ�����ʹ�ø��㣬���Խ�����߳̽���ͨ�ţ�����ɸ��Ӹ��ӵ�ͨ�š�
 * 
 * ��Condition�У���await()�滻wait()����signal()�滻notify()����signalAll()�滻notifyAll()����ͳ�̵߳�ͨ�ŷ�ʽ��Condition������ʵ�֣�
 * ����ע�⣬Condition�Ǳ��󶨵�Lock�ϵģ�Ҫ����һ��Lock��Condition������newCondition()������
 * 
 * Condition��ǿ��֮������������Ϊ����̼߳佨����ͬ��Condition
 * 
 * @Title: ConditionTest
 * @author tangjia
 * @date 2018-3-4 ����4:04:19
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
                    System.out.println("����Ҫ��һ���ź�"+this);
                    condition.await();
                    System.out.println("���õ�һ���ź�"+this);
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
                    System.out.println("���õ�����");
                    Thread.sleep(500);
                    System.out.println("�ҷ���һ���ź�");
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