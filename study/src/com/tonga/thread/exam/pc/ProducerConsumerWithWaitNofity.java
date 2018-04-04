package com.tonga.thread.exam.pc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ������-�����飬wait �� notifyʵ��
 * �������������ݵ��������У������ߴӻ�������ȡ���ݡ�
 * ����������Ѿ����ˣ����������߳������� 
 * ���������Ϊ�գ���ô�������߳�������
 * 
 * synchronized��wait��notifyʵ��
 * 
 * <p>Title: ProducerConsumer.java</p>  
 * @author tangjia
 * @date 2018-3-13 ����5:51:46 
 * @version 1.0
 */
public class ProducerConsumerWithWaitNofity {
    public static void main(String[] args) {
    	final Resource resource = new Resource(10);
    	ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 3; i++) {
			exec.execute(new Runnable() {
				public void run() {
					new Producer(resource).start();
				}
			});
	    	exec.execute(new Runnable() {
				public void run() {
					new Consumer(resource).start();
				}
			});
		}
    	
    	exec.shutdown();
    	
    }
}

/**
 * ������Դ��
 * <p>Title: ProducerConsumerWithWaitNofity.java</p>  
 * @author tangjia
 * @date 2018-3-14 ����9:48:45 
 * @version 1.0
 */
class Resource{
	int num;
	int size;
	
	public Resource(int size){
		this.size = size;
	}
	
	public synchronized void add() {
		try {
			if (num < size) {
				num++;
				System.out.println(Thread.currentThread().getName()+"-[producer]������Դ�ɹ����ܹ���Դ��" + num);
				notifyAll();	//֪ͨ������������Դ
			}else{
				System.out.println(Thread.currentThread().getName()+"-[producer]��Դ�������ȴ�...");
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ������������Դ
	 */
	public synchronized void remove(){
		try {
			if(num > 0){
				num--;
				System.out.println(Thread.currentThread().getName()+"-[consumer]������Դ�ɹ���ʣ����Դ��" + num);
				notifyAll();	//֪ͨ������������Դ
			}else{
				System.out.println(Thread.currentThread().getName()+"-[consumer]��ʱ����Դ���ȴ�...");
				wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

/**
 * �������߳�
 * <p>Title: ProducerConsumerWithWaitNofity.java</p>  
 * @author tangjia
 * @date 2018-3-14 ����11:25:53 
 * @version 1.0
 */
class Consumer extends Thread {
	Resource resource;
	
	public Consumer(Resource resource){
		this.resource = resource;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resource.remove();
		}
	}
}

class Producer extends Thread{
	Resource resource;
	
	public Producer(Resource resource){
		this.resource = resource;
	}
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resource.add();
		}
	}
}
    
