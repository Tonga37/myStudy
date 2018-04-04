package com.tonga.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestClient {
    public static void main(String[] args) {
        
        ExecutorService executor = Executors.newCachedThreadPool();
        
        long begin = System.currentTimeMillis();
        
        for (int i = 0; i < 10; i++) {
            //new Thread(new MyThread("testnopoolThread"+i)).start();
            
            ThreadPool.getInstatce().start(new MyThread("testpoolThread"+i));
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            //executor.execute(new MyThread("executorpoolThread"+i));
        }
    
    }
}