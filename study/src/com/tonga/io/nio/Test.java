package com.tonga.io.nio;
import java.util.Scanner;
/**
 * ���Է���
 * @author tangjia
 * @version 1.0
 */
public class Test {
	//����������
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
		//���з�����
		Server.start();
		//����ͻ������ڷ���������ǰִ�д���
		Thread.sleep(100);
		//���пͻ��� 
		Client.start();
		while(Client.sendMsg(new Scanner(System.in).nextLine()));
	}
}