package com.tonga.thread.exam;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 有四个线程1、2、3、4。线程1的功能就是输出1，线程2的功能就是输出2，以此类推.........现在有四个文件ABCD。
 * 初始都为空。现要让四个文件呈如下格式：
 *	A：1 2 3 4 1 2.... 
 * 	B：2 3 4 1 2 3....
 *	C：3 4 1 2 3 4.... 
 *	D：4 1 2 3 4 1....
 *
 *	解题思路：
 *	举生活中类似例子，有4位老师1（语文）、2（数学）、3（英语）、4（计算机），需要给4个班级（A B C D）教课
 *	思考：4个班级的课程表肯定是把课程错开，让每个班级每节课都有老师教课。不可能等4位老师给A班级上完所有课程，然后在给B班级上课，直至所有班级课程都上完。
 *
 * <p>Title: ThreadExam2.java</p>  
 * @author tangjia
 * @date 2018-3-17 上午11:02:36 
 * @version 1.0
 */
public class ThreadExam2 {
	
	public static volatile String flag1 = "1";	//控制线程按1 2 3 4顺序写入
	public static volatile String flag2 = "2";	//控制线程按2 3 4 1顺序写入
	public static volatile String flag3 = "3";	//控制线程按3 4 1 2顺序写入
	public static volatile String flag4 = "4";	//控制线程按4 1 2 3顺序写入
	
	public static void main(String[] args) {
		ReentrantLock lockA = new ReentrantLock();	//锁针对文件A
		Condition conditionA1 = lockA.newCondition();
		Condition conditionA2 = lockA.newCondition();
		Condition conditionA3 = lockA.newCondition();
		Condition conditionA4 = lockA.newCondition();
		
		ReentrantLock lockB = new ReentrantLock();
		Condition conditionB1 = lockB.newCondition();
		Condition conditionB2 = lockB.newCondition();
		Condition conditionB3 = lockB.newCondition();
		Condition conditionB4 = lockB.newCondition();
		
		ReentrantLock lockC = new ReentrantLock();
		Condition conditionC1 = lockC.newCondition();
		Condition conditionC2 = lockC.newCondition();
		Condition conditionC3 = lockC.newCondition();
		Condition conditionC4 = lockC.newCondition();
		
		ReentrantLock lockD = new ReentrantLock();
		Condition conditionD1 = lockD.newCondition();
		Condition conditionD2 = lockD.newCondition();
		Condition conditionD3 = lockD.newCondition();
		Condition conditionD4 = lockD.newCondition();
		
		Out outA = new Out("e:\\A.txt");
		Out outB = new Out("e:\\B.txt");
		Out outC = new Out("e:\\C.txt");
		Out outD = new Out("e:\\D.txt");
		
		/**
		 * 1号线程资源（线程1写入各文件顺序：文件A-1；文件B-4；文件C-3；文件D-2）
		 */
		Out[] out1 = new Out[]{outA,outB,outC,outD};
		Lock[] lock1 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition1 = new Condition[]{conditionA1,conditionB4,conditionC3,conditionD2};
		Condition[] nextCondition1 = new Condition[]{conditionA2,conditionB1,conditionC4,conditionD3};
		/**
		 * 2号线程资源（线程2写入各文件顺序：文件A-2；文件B-1；文件C-4；文件D-3）
		 */
		Out[] out2 = new Out[]{outA,outB,outC,outD};
		Lock[] lock2 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition2 = new Condition[]{conditionA2,conditionB1,conditionC4,conditionD3};
		Condition[] nextCondition2 = new Condition[]{conditionA3,conditionB2,conditionC1,conditionD4};
		/**
		 * 3号线程资源（线程3写入各文件顺序：文件A-3；文件B-2；文件C-1；文件D-4）
		 */
		Out[] out3 = new Out[]{outA,outB,outC,outD};
		Lock[] lock3 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition3 = new Condition[]{conditionA3,conditionB2,conditionC1,conditionD4};
		Condition[] nextCondition3 = new Condition[]{conditionA4,conditionB3,conditionC2,conditionD1};
		/**
		 * 4号线程资源（线程4写入各文件顺序：文件A-4；文件B-3；文件C-2；文件D-1）
		 */
		Out[] out4 = new Out[]{outA,outB,outC,outD};
		Lock[] lock4 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition4 = new Condition[]{conditionA4,conditionB3,conditionC2,conditionD1};
		Condition[] nextCondition4 = new Condition[]{conditionA1,conditionB4,conditionC3,conditionD2};
		
		Thread work1 = new WorkThread2(out1, "1", lock1, currentCondition1, nextCondition1);
		Thread work2 = new WorkThread2(out2, "2", lock2, currentCondition2, nextCondition2);
		Thread work3 = new WorkThread2(out3, "3", lock3, currentCondition3, nextCondition3);
		Thread work4 = new WorkThread2(out4, "4", lock4, currentCondition4, nextCondition4);
		
		try {
			work1.start();
			work2.start();
			work3.start();
			work4.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


class WorkThread2 extends Thread{
	private Out[] out;	////每个文件的位置，需要和文件锁位置对应
	private Lock[] lock;	//每个文件锁的位置，和当前条件下一个条件需要对应
	private Condition[] currentCondition;	//每一个文件锁的当前条件和下一个条件锁需要对应
	private Condition[] nextCondition;
	private String workContent;
	
	public WorkThread2(Out[] out, String workContent,Lock[] lock, Condition[] currentCondition, Condition[] nextCondition){
		this.out = out;
		this.workContent = workContent;
		this.lock = lock;
		this.currentCondition = currentCondition;
		this.nextCondition = nextCondition;
	}
	
	@Override
	public void run() {
		int i = Integer.parseInt(workContent) - 1;	//重要：控制每个线程第1次需要写入哪个文件
		while(true){
			int index=0;
			index = i%4;
			/**
			 * A文件写入顺序1 2 3 4
			 */
			if(0 == index && ThreadExam2.flag1.equals(this.workContent)){
				ThreadExam2.flag1 = controlWrite(index, ThreadExam2.flag1, "4");
			}
			/**
			 * B文件时，2 3 4 1
			 */
			if(1 == index && ThreadExam2.flag2.equals(this.workContent)){
				ThreadExam2.flag2 = controlWrite(index, ThreadExam2.flag2, "1");
			}
			/**
			 * C文件时，3 4 1 2
			 */
			if(2 == index && ThreadExam2.flag3.equals(this.workContent)){
				ThreadExam2.flag3 = controlWrite(index, ThreadExam2.flag3, "2");
			}
			/**
			 * D文件时，4 1 2 3
			 */
			if(3 == index && ThreadExam2.flag4.equals(this.workContent)){
				ThreadExam2.flag4 = controlWrite(index, ThreadExam2.flag4, "3");
			}
			
			i++;
		}
	}

	/**
	 * 控制各线程写入各文件顺序
	 * @param index
	 * @param flag
	 * @param stop
	 * @return
	 */
	private String controlWrite(int index, String flag, String stopContent) {
		if(lock[index].tryLock()){
			try {
				Thread.sleep(1000);
				String content  = workContent;
				if(stopContent.equals(workContent)){
					content = workContent + "\r\n";
				}
				out[index].writeFile(content);
				nextCondition[index].signal();
				
				//控制线程写入数据顺序
				if ("1".equals(flag)) {
					flag = "2";
				}else if("2".equals(flag)) {
					flag = "3";
				}else if("3".equals(flag)) {
					flag = "4";
				}else if("4".equals(flag)) {
					flag = "1";
				}
				//currentCondition[index].await();	//不能阻塞线程，需要获取其他文件锁写入数据
			}catch (Exception e) {
				e.printStackTrace();
			}finally{
				lock[index].unlock();
			}
		}
		return flag;
	}
}

/**
 * 写入文件工具类
 * <p>Title: ThreadExam2.java</p>  
 * @author tangjia
 * @date 2018-3-19 上午11:00:00 
 * @version 1.0
 */
class Out{
	private String fileName;
	
	public Out(String fileName){
		this.fileName = fileName;
	}
	
	private File out() {
		File file = null;
		try {
			file = new File(this.fileName);
			if(!file.exists()){
				file.createNewFile();
			}	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	public void writeFile(String content) {
		File file = this.out();
		
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
            out.write(content); 
            System.out.println(Thread.currentThread().getName() + "-写入成功文件【"+fileName+"】：" + content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}

