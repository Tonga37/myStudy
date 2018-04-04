package com.tonga.thread.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * �����ࣺAtomicBoolean��AtomicInteger��AtomicLong��AtomicReference
 * �����ࣺAtomicIntegerArray��AtomicLongArray��AtomicReferenceArray
 * �������ࣺAtomicLongFieldUpdater��AtomicIntegerFieldUpdater��AtomicReferenceFieldUpdater
 * ���ϱ����ࣺAtomicMarkableReference��AtomicStampedReference
 * 
 * ��һ��AtomicBoolean��AtomicInteger��AtomicLong��AtomicReference�����ֻ��������������������������������������������ݣ�
 * ���ڲ�ʵ�ֲ��Ǽ򵥵�ʹ��synchronized������һ����Ϊ��Ч�ķ�ʽCAS (compare and swap) + volatile��native������
 * �Ӷ�������synchronized�ĸ߿�����ִ��Ч�ʴ�Ϊ������
 * 
 * <p>Title: AtomicTest.java</p>  
 * @author tangjia
 * @date 2018-3-7 ����2:29:15 
 * @version 1.0
 */
public class AtomicTest {
	
	public static void main(String[] args) {
		AtomicInteger ai = new AtomicInteger();
		System.out.println(ai);
		ai.getAndIncrement();
		System.out.println(ai);
	}

}
