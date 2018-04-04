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
 * ���ĸ��߳�1��2��3��4���߳�1�Ĺ��ܾ������1���߳�2�Ĺ��ܾ������2���Դ�����.........�������ĸ��ļ�ABCD��
 * ��ʼ��Ϊ�ա���Ҫ���ĸ��ļ������¸�ʽ��
 *	A��1 2 3 4 1 2.... 
 * 	B��2 3 4 1 2 3....
 *	C��3 4 1 2 3 4.... 
 *	D��4 1 2 3 4 1....
 *
 *	����˼·��
 *	���������������ӣ���4λ��ʦ1�����ģ���2����ѧ����3��Ӣ���4�������������Ҫ��4���༶��A B C D���̿�
 *	˼����4���༶�Ŀγ̱�϶��ǰѿγ̴�����ÿ���༶ÿ�ڿζ�����ʦ�̿Ρ������ܵ�4λ��ʦ��A�༶�������пγ̣�Ȼ���ڸ�B�༶�ϿΣ�ֱ�����а༶�γ̶����ꡣ
 *
 * <p>Title: ThreadExam2.java</p>  
 * @author tangjia
 * @date 2018-3-17 ����11:02:36 
 * @version 1.0
 */
public class ThreadExam2 {
	
	public static volatile String flag1 = "1";	//�����̰߳�1 2 3 4˳��д��
	public static volatile String flag2 = "2";	//�����̰߳�2 3 4 1˳��д��
	public static volatile String flag3 = "3";	//�����̰߳�3 4 1 2˳��д��
	public static volatile String flag4 = "4";	//�����̰߳�4 1 2 3˳��д��
	
	public static void main(String[] args) {
		ReentrantLock lockA = new ReentrantLock();	//������ļ�A
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
		 * 1���߳���Դ���߳�1д����ļ�˳���ļ�A-1���ļ�B-4���ļ�C-3���ļ�D-2��
		 */
		Out[] out1 = new Out[]{outA,outB,outC,outD};
		Lock[] lock1 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition1 = new Condition[]{conditionA1,conditionB4,conditionC3,conditionD2};
		Condition[] nextCondition1 = new Condition[]{conditionA2,conditionB1,conditionC4,conditionD3};
		/**
		 * 2���߳���Դ���߳�2д����ļ�˳���ļ�A-2���ļ�B-1���ļ�C-4���ļ�D-3��
		 */
		Out[] out2 = new Out[]{outA,outB,outC,outD};
		Lock[] lock2 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition2 = new Condition[]{conditionA2,conditionB1,conditionC4,conditionD3};
		Condition[] nextCondition2 = new Condition[]{conditionA3,conditionB2,conditionC1,conditionD4};
		/**
		 * 3���߳���Դ���߳�3д����ļ�˳���ļ�A-3���ļ�B-2���ļ�C-1���ļ�D-4��
		 */
		Out[] out3 = new Out[]{outA,outB,outC,outD};
		Lock[] lock3 = new ReentrantLock[]{lockA,lockB,lockC,lockD};
		Condition[] currentCondition3 = new Condition[]{conditionA3,conditionB2,conditionC1,conditionD4};
		Condition[] nextCondition3 = new Condition[]{conditionA4,conditionB3,conditionC2,conditionD1};
		/**
		 * 4���߳���Դ���߳�4д����ļ�˳���ļ�A-4���ļ�B-3���ļ�C-2���ļ�D-1��
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
	private Out[] out;	////ÿ���ļ���λ�ã���Ҫ���ļ���λ�ö�Ӧ
	private Lock[] lock;	//ÿ���ļ�����λ�ã��͵�ǰ������һ��������Ҫ��Ӧ
	private Condition[] currentCondition;	//ÿһ���ļ����ĵ�ǰ��������һ����������Ҫ��Ӧ
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
		int i = Integer.parseInt(workContent) - 1;	//��Ҫ������ÿ���̵߳�1����Ҫд���ĸ��ļ�
		while(true){
			int index=0;
			index = i%4;
			/**
			 * A�ļ�д��˳��1 2 3 4
			 */
			if(0 == index && ThreadExam2.flag1.equals(this.workContent)){
				ThreadExam2.flag1 = controlWrite(index, ThreadExam2.flag1, "4");
			}
			/**
			 * B�ļ�ʱ��2 3 4 1
			 */
			if(1 == index && ThreadExam2.flag2.equals(this.workContent)){
				ThreadExam2.flag2 = controlWrite(index, ThreadExam2.flag2, "1");
			}
			/**
			 * C�ļ�ʱ��3 4 1 2
			 */
			if(2 == index && ThreadExam2.flag3.equals(this.workContent)){
				ThreadExam2.flag3 = controlWrite(index, ThreadExam2.flag3, "2");
			}
			/**
			 * D�ļ�ʱ��4 1 2 3
			 */
			if(3 == index && ThreadExam2.flag4.equals(this.workContent)){
				ThreadExam2.flag4 = controlWrite(index, ThreadExam2.flag4, "3");
			}
			
			i++;
		}
	}

	/**
	 * ���Ƹ��߳�д����ļ�˳��
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
				
				//�����߳�д������˳��
				if ("1".equals(flag)) {
					flag = "2";
				}else if("2".equals(flag)) {
					flag = "3";
				}else if("3".equals(flag)) {
					flag = "4";
				}else if("4".equals(flag)) {
					flag = "1";
				}
				//currentCondition[index].await();	//���������̣߳���Ҫ��ȡ�����ļ���д������
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
 * д���ļ�������
 * <p>Title: ThreadExam2.java</p>  
 * @author tangjia
 * @date 2018-3-19 ����11:00:00 
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
            System.out.println(Thread.currentThread().getName() + "-д��ɹ��ļ���"+fileName+"����" + content);
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

