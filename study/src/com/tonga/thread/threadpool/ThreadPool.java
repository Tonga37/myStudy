package com.tonga.thread.threadpool;

import java.util.List;
import java.util.Vector;

public class ThreadPool {
    private static ThreadPool instance = null;
    //空闲线程队列
    private List<PThread> idelThreads;
    //已有的线程总数
    private int threadCounter;
    private boolean isShutdown = false;
    
    public ThreadPool() {
        idelThreads = new Vector<PThread>(5);
        threadCounter=0;
    }

    public synchronized int getCreatedThreadsCount() {
        
        return threadCounter;
    }
    
    //取得线程池实例
    public synchronized static ThreadPool getInstatce(){
        if(instance==null){
            instance = new ThreadPool();
        }
        return instance;
    }
    
    //把线程重新放回到池中
    
    public synchronized void repool(PThread repoolThread){
        if(!isShutdown){
            idelThreads.add(repoolThread);
        }else{
            repoolThread.shutdown();
        }
    }
    
    //停止池中所有线程
    public synchronized void shutdown(){
        isShutdown = true;
        for (int i = 0; i < idelThreads.size(); i++) {
            PThread pthread = idelThreads.get(i);
            pthread.shutdown();
        }
    }
    //执行任务
    public synchronized void start(Runnable target){
        PThread pthread = null;
        //如果有闲置线程
        if(idelThreads.size()>0){
            int index = idelThreads.size()-1;
            pthread=idelThreads.get(index);
            idelThreads.remove(index);
            pthread.setTarget(target);
        }else{//如果没有闲置线程
            threadCounter++;
            PThread p = new PThread(instance, target, "PThread#"+threadCounter);
            p.start();
        }
    }
    
    
    
}