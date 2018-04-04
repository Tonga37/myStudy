package com.tonga.io.aio;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import com.tonga.io.aio.server.ReadHandler;

/**
 * 
 * <p>Title: AcceptHandler.java</p>  
 * @author tangjia
 * @date 2018-3-9 下午5:40:01 
 * @version 1.0
 */
public class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncServerHandler> {
	@Override
	public void completed(AsynchronousSocketChannel channel,AsyncServerHandler serverHandler) {
		//继续接受其他客户端的请求
		Server.clientCount++;
		System.out.println("连接的客户端数：" + Server.clientCount);
		serverHandler.channel.accept(serverHandler, this);
		//创建新的Buffer
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		//异步读  第三个参数为接收消息回调的业务Handler
		channel.read(buffer, buffer, new ReadHandler(channel));
	}
	@Override
	public void failed(Throwable exc, AsyncServerHandler serverHandler) {
		exc.printStackTrace();
		serverHandler.latch.countDown();
	}
}