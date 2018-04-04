package com.tonga.thread.pc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * 生产者-消费者模式是经典的多线程设计模式，它为多线程间的协作提供了良好的解决方案。
 * 在生产者-消费者模式中，有两类线程：若干个生产者线程和若干个消费者线程。生产者负责提交用户请求，消费者用于具体的处理生产者提交的任务。
 * 生产者和消费者通过共享内存缓冲区进行数据通信。
 * @author tangjia
 *
 */
public class Client {
	
	/**
	 * 优点：生产者-消费者模式能很好的对生产者线程和消费者线程进行解耦，优化系统的整体结构。
	 * 同时，由于缓冲区的存在，运行生产者和消费者在性能上存在一定的差异，从而一定程度上缓解了性能瓶颈对系统性能的影响。
	 * @param args
	 * @throws InterruptedException
	 */
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingQueue<PCData>(10); 
        
        Producer  p1 = new Producer(queue);
        Producer  p2 = new Producer(queue);
        Producer  p3 = new Producer(queue);
        
        Consumer  c1 = new Consumer(queue);
        Consumer  c2 = new Consumer(queue);
        Consumer  c3 = new Consumer(queue);
        
        ExecutorService exe = Executors.newCachedThreadPool();
        exe.execute(p1);
        exe.execute(p2);
        exe.execute(p3);
        
        exe.execute(c1);
        exe.execute(c2);
        exe.execute(c3);
        
        Thread.sleep(10*1000);
        
        p1.stop();
        p2.stop();
        p3.stop();
        
        Thread.sleep(3000);
        exe.shutdown();
    }
}