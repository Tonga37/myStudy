package com.tonga.io.aio;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.tonga.io.aio.server.ReadHandler;

/**
 * 
 * <p>Title: AcceptHandler.java</p>  
 * @author tangjia
 * @date 2018-3-9 ����5:40:01 
 * @version 1.0
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {
	@Override
	public void completed(AsynchronousSocketChannel channel,AsyncServerHandler serverHandler) {
		//�������������ͻ��˵�����
		Server.clientCount++;
		System.out.println("���ӵĿͻ�������" + Server.clientCount);
		serverHandler.channel.accept(serverHandler, this);
		//�����µ�Buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//�첽��  ����������Ϊ������Ϣ�ص���ҵ��Handler
		channel.read(buffer, buffer, new ReadHandler(channel));
	}
	@Override
	public void failed(Throwable exc, AsyncServerHandler serverHandler) {
		exc.printStackTrace();
		serverHandler.latch.countDown();
	}
}