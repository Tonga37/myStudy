package com.tonga.thread.threadpool;
public class PThread extends Thread{
    //线程池
    private ThreadPool pool;
    //任务
    private Runnable target;
    private boolean isShutDown = false;
    private boolean isIdle = false;
    public PThread(ThreadPool pool, Runnable target,String name) {
        super(name);
        this.pool = pool;
        this.target = target;
    }
    
    public synchronized Runnable getTarget() {
        return target;
    }
    public synchronized boolean isIdle() {
        return isIdle;
    }

    @Override
    public void run() {
        
        while(!isShutDown){
            isIdle = false;
            
            if(target!=null){
                //运行任务
            	System.out.println("PThread.name：" + this.getName());
                target.run();
            }
            //任务结束，闲置任务
            isIdle=true;
            
            try {
                pool.repool(this);
                synchronized (this) {
                    //线程闲置，等待任务到来
                    wait();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
            
            isIdle=false;
        }
    
    }
    
    public synchronized void setTarget(Runnable target){
        this.target=target;
        //设置任务之后，通知run方法，开始执行
        notifyAll();
    }
    
    public synchronized void shutdown(){
        isShutDown=true;
        notifyAll();
    }
    
    
}