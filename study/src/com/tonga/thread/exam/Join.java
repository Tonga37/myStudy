package com.tonga.thread.exam;

/**
 * 现在有T1、T2、T3三个线程，你怎样保证T2在T1执行完后执行，T3在T2执行完后执行？
 * @Title: exam1
 * @author tangjia
 * @date 2018-3-2 上午10:58:23
 */
public class Join {
	
	public static void main(String[] args) {
		SubThread t1 = new SubThread("T1");
		SubThread t2 = new SubThread("T2");
		SubThread t3 = new SubThread("T3");
		
		try {
			t1.start();
			t1.join();
			
			t2.start();
			t2.join();
			
			t3.start();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class SubThread extends Thread{
	
	public SubThread(String name) {
		super(name);
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(this.getName() + ":" + i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
