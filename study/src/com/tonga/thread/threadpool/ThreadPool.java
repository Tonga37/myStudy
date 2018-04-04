package com.tonga.thread.threadpool;

import java.util.List;
import java.util.Vector;

public class ThreadPool {
    private static ThreadPool instance = null;
    //�����̶߳���
    private List<PThread> idelThreads;
    //���е��߳�����
    private int threadCounter;
    private boolean isShutdown = false;
    
    public ThreadPool() {
        idelThreads = new Vector<PThread>(5);
        threadCounter=0;
    }

    public synchronized int getCreatedThreadsCount() {
        
        return threadCounter;
    }
    
    //ȡ���̳߳�ʵ��
    public synchronized static ThreadPool getInstatce(){
        if(instance==null){
            instance = new ThreadPool();
        }
        return instance;
    }
    
    //���߳����·Żص�����
    
    public synchronized void repool(PThread repoolThread){
        if(!isShutdown){
            idelThreads.add(repoolThread);
        }else{
            repoolThread.shutdown();
        }
    }
    
    //ֹͣ���������߳�
    public synchronized void shutdown(){
        isShutdown = true;
        for (int i = 0; i < idelThreads.size(); i++) {
            PThread pthread = idelThreads.get(i);
            pthread.shutdown();
        }
    }
    //ִ������
    public synchronized void start(Runnable target){
        PThread pthread = null;
        //����������߳�
        if(idelThreads.size()>0){
            int index = idelThreads.size()-1;
            pthread=idelThreads.get(index);
            idelThreads.remove(index);
            pthread.setTarget(target);
        }else{//���û�������߳�
            threadCounter++;
            PThread p = new PThread(instance, target, "PThread#"+threadCounter);
            p.start();
        }
    }
    
    
    
}