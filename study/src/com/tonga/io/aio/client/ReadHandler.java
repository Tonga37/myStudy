package com.tonga.io.aio.client;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * <p>Title: ReadHandler.java</p>  
 * @author tangjia
 * @date 2018-3-9 ����5:44:55 
 * @version 1.0
 */
public class ReadHandler implements CompletionHandler<Integer, ByteBuffer> {
	private AsynchronousSocketChannel clientChannel;
	private CountDownLatch latch;
	
	public ReadHandler(AsynchronousSocketChannel clientChannel,CountDownLatch latch) {
		this.clientChannel = clientChannel;
		this.latch = latch;
	}
	@Override
	public void completed(Integer result,ByteBuffer buffer) {
		buffer.flip();
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		String body;
		try {
			body = new String(bytes,"UTF-8");
			System.out.println("�ͻ����յ����:"+ body);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void failed(Throwable exc,ByteBuffer attachment) {
		System.err.println("���ݶ�ȡʧ��...");
		try {
			clientChannel.close();
			latch.countDown();
		} catch (IOException e) {
		}
	}
}