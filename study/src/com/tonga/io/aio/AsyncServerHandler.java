package com.tonga.io.aio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * <p>Title: AsyncServerHandler.java</p>  
 * @author tangjia
 * @date 2018-3-9 ����5:39:27 
 * @version 1.0
 */
public class AsyncServerHandler implements Runnable {
	public CountDownLatch latch;
	public AsynchronousServerSocketChannel channel;
	public AsyncServerHandler(int port) {
		try {
			//���������ͨ��
			channel = AsynchronousServerSocketChannel.open();
			//�󶨶˿�
			channel.bind(new InetSocketAddress(port));
			System.out.println("���������������˿ںţ�" + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		//CountDownLatch��ʼ��
		//�������ã������һ������ִ�еĲ���֮ǰ��������ǰ���ֳ�һֱ����
		//�˴������ֳ��ڴ���������ֹ�����ִ����ɺ��˳�
		//Ҳ����ʹ��while(true)+sleep 
		//���ɻ����Ͳ���Ҫ����������⣬��Ϊ������ǲ����˳���
		latch = new CountDownLatch(1);
		//���ڽ��տͻ��˵�����
		channel.accept(this,new AcceptHandler());
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}