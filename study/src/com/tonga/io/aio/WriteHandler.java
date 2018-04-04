package com.tonga.io.aio;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

import com.tonga.io.aio.client.ReadHandler;

/**
 * 
 * <p>Title: WriteHandler.java</p>  
 * @author tangjia
 * @date 2018-3-9 下午5:42:31 
 * @version 1.0
 */
public class WriteHandler implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	public WriteHandler(AsynchronousSocketChannel clientChannel,CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}
	@Override
	public void completed(Integer result, ByteBuffer buffer) {
		//完成全部数据的写入
		if (buffer.hasRemaining()) {
			clientChannel.write(buffer, buffer, this);
		}
		else {
			//读取数据
			ByteBuffer readBuffer = ByteBuffer.allocate(1024);
			clientChannel.read(readBuffer,readBuffer,new ReadHandler(clientChannel, latch));
		}
	}
	@Override
	public void failed(Throwable exc, ByteBuffer attachment) {
		System.err.println("数据发送失败...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
		}
	}
}