package com.tonga.thread.thread.join;

/**
 * 
 * @Title: JoinTest
 * @author tangjia
 * @date 2018-3-4 下午3:12:59
 */
public class JoinTest implements Runnable{

    /**
     * join方法就是通过wait方法来将线程的阻塞，如果join的线程还在执行，则将当前线程阻塞起来，直到join的线程执行完成，当前线程才能执行。
     * 不过有一点需要注意，这里的join只调用了wait方法，却没有对应的notify方法，原因是Thread的start方法中做了相应的处理，
     * 所以当join的线程执行完成以后，会自动唤醒主线程继续往下执行。
     * @param args
     */
    public static void main(String[] args) {
        for (int i=0;i<3;i++) {
            Thread test = new Thread(new JoinTest());
            test.start();
            try {
            	/**
            	 * 在没有使用join方法之间，线程是并发执行的，而使用join方法后，所有线程是顺序执行的。
            	 */
                test.join(); //调用join方法
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Finished~~~");
    }
    
    @Override
    public void run() {

        try {
            System.out.println(Thread.currentThread().getName() + " start-----");
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " end------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}