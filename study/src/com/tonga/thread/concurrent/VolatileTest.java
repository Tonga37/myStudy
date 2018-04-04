package com.tonga.thread.concurrent;

/**
 * �ɼ���������Ҫָһ���߳��޸��˹������ֵ������һ���߳�ȴ������������ɼ����������Ҫԭ����ÿ���߳�ӵ���Լ���һ�����ٻ����������̹߳����ڴ档
 * volatile�ؼ�������Ч�Ľ��������⣬���ǿ�����������ӣ��Ϳ���֪�������ã�
 * ֱ����˵����δ���Ľ��ֻ���������֣�b=3;a=3 �� b=2;a=1��������������Ĵ��루����ʱ����Ҫ��һ�㣩����ᷢ�ֳ��������ֽ��֮�⣬�������˵����ֽ����
 * b=3;a=1
 * 
 * ԭ��Ϊʲô�����b=3;a=1���ֽ���أ���������£������ִ��change��������ִ��print������������Ӧ��Ϊb=3;a=3��
 * �෴�������ִ�е�print��������ִ��change���������Ӧ���� b=2;a=1��
 * ��b=3;a=1�Ľ������ô�����ģ�ԭ����ǵ�һ���߳̽�ֵa=3�޸ĺ󣬵��ǶԵڶ����߳��ǲ��ɼ��ģ����Բų�����һ�����
 * 
 * ����������a��b���ĳ�volatile���͵ı�����ִ�У�����Ҳ�������b=3;a=1�Ľ���ˡ�
 * 
 * �ɼ��ԣ��ɼ���������Ҫָһ���߳��޸��˹������ֵ������һ���߳�ȴ������������ɼ����������Ҫԭ����ÿ���߳�ӵ���Լ���һ�����ٻ����������̹߳����ڴ档volatile�ؼ�������Ч�Ľ���������
 * ԭ���ԣ�volatileֻ�ܱ�֤�Ե��ζ�/д��ԭ���ԡ�
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