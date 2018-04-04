package com.tonga.thread.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 标量类：AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference
 * 数组类：AtomicIntegerArray，AtomicLongArray，AtomicReferenceArray
 * 更新器类：AtomicLongFieldUpdater，AtomicIntegerFieldUpdater，AtomicReferenceFieldUpdater
 * 复合变量类：AtomicMarkableReference，AtomicStampedReference
 * 
 * 第一组AtomicBoolean，AtomicInteger，AtomicLong，AtomicReference这四种基本类型用来处理布尔，整数，长整数，对象四种数据，
 * 其内部实现不是简单的使用synchronized，而是一个更为高效的方式CAS (compare and swap) + volatile和native方法，
 * 从而避免了synchronized的高开销，执行效率大为提升。
 * 
 * <p>Title: AtomicTest.java</p>  
 * @author tangjia
 * @date 2018-3-7 下午2:29:15 
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
