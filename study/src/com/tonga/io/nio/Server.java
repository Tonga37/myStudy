package com.tonga.io.nio;

/**
 * 
 * <p>Title: Server.java</p>  
 * @author tangjia
 * @date 2018-3-9 обнГ5:34:19 
 * @version 1.0
 */
public class Server {
	private static int DEFAULT_PORT = 12345;
	private static ServerHandle serverHandle;
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static synchronized void start(int port){
		if(serverHandle!=null)
			serverHandle.stop();
		serverHandle = new ServerHandle(port);
		new Thread(serverHandle,"Server").start();
	}
	public static void main(String[] args){
		start();
	}
}