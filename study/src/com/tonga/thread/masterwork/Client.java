package com.tonga.thread.masterwork;

import java.util.Map;
import java.util.Set;

/**
 * Master-Workerģʽ�ǳ��õĲ������ģʽ�����ĺ���˼���ǣ�ϵͳ����������Э�鹤����Master���̺�Worker���̡�
 * Master���̸�����պͷ�������Worker���̸����������񡣵�����Worker���̽�����������󣬽�������ظ�Master���̣���Master���й��ɺͻ��ܣ��Ӷ��õ�ϵͳ�����
 * @author tangjia
 *
 */
public class Client {
	
	/**
	 * Master-Workerģʽ�ĺô��ǣ����ܽ�������ֽ�����ɸ�С���񣬲���ִ�У��Ӷ����ϵͳ���ܡ�
	 * ������ϵͳ������Client��˵������һ���ύ��Master���̾ͻ����̷��������������أ��������ϵͳ������ȫ�������ٷ��أ��䴦��������첽�ġ�
	 * 
	 */
    public static void main(String[] args) {
        Master m = new Master(new PlusWorker(), 5);//��������̴߳���
        for (int i = 0; i < 100; i++) {
            m.submit(i);
        }
        m.execute();
        int re = 0;
        Map<String, Object> resultMap = m.getResultMap();
        while(resultMap.size()>0||!m.isComplete()){
            Set<String> keys = resultMap.keySet();
            String key =  null;
            for(String k:keys){
                key=k;
                break;
            }
            Integer i = null;
            if(key != null){
                i = (Integer) resultMap.get(key);
            }
            if(i!=null){
                re+=i;//���м�������
            }
            
            if(key!=null){
                resultMap.remove(key);//��������ɵĽ���Ƴ�
            }
        }
        
        System.out.println(re);
    }
}