package com.tonga.thread.threadpool;

public class MyThread implements Runnable{
    
    
    private String name;
    
    
    
    public MyThread() {
        
    }


    public MyThread(String name) {
        this.name = name;
    }


    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            Thread.sleep(1000);
            System.out.println(this.name);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}