package com.tonga.thread.concurrent;

/**
 * 可见性问题主要指一个线程修改了共享变量值，而另一个线程却看不到。引起可见性问题的主要原因是每个线程拥有自己的一个高速缓存区——线程工作内存。
 * volatile关键字能有效的解决这个问题，我们看下下面的例子，就可以知道其作用：
 * 直观上说，这段代码的结果只可能有两种：b=3;a=3 或 b=2;a=1。不过运行上面的代码（可能时间上要长一点），你会发现除了上两种结果之外，还出现了第三种结果：
 * b=3;a=1
 * 
 * 原因：为什么会出现b=3;a=1这种结果呢？正常情况下，如果先执行change方法，再执行print方法，输出结果应该为b=3;a=3。
 * 相反，如果先执行的print方法，再执行change方法，结果应该是 b=2;a=1。
 * 那b=3;a=1的结果是怎么出来的？原因就是第一个线程将值a=3修改后，但是对第二个线程是不可见的，所以才出现这一结果。
 * 
 * 解决：如果将a和b都改成volatile类型的变量再执行，则再也不会出现b=3;a=1的结果了。
 * 
 * 可见性：可见性问题主要指一个线程修改了共享变量值，而另一个线程却看不到。引起可见性问题的主要原因是每个线程拥有自己的一个高速缓存区——线程工作内存。volatile关键字能有效的解决这个问题
 * 原子性：volatile只能保证对单次读/写的原子性。
 * 
 * @author tangjia
 *
 */
public class VolatileTest {
	volatile int a = 1;
	volatile int b = 2;

    public void change(){
        a = 3;
        b = a;
    }

    public void print(){
    	if(a==1 && b==3){
    		System.out.println("b="+b+";a="+a);
    	}
    }

    public static void main(String[] args) {
        while (true){
            final VolatileTest test = new VolatileTest();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.change();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.print();
                }
            }).start();

        }
    }
}