package com.tonga.thread.threadpool;
public class PThread extends Thread{
    //�̳߳�
    private ThreadPool pool;
    //����
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
                //��������
            	System.out.println("PThread.name��" + this.getName());
                target.run();
            }
            //�����������������
            isIdle=true;
            
            try {
                pool.repool(this);
                synchronized (this) {
                    //�߳����ã��ȴ�������
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
        //��������֮��֪ͨrun��������ʼִ��
        notifyAll();
    }
    
    public synchronized void shutdown(){
        isShutDown=true;
        notifyAll();
    }
    
    
}