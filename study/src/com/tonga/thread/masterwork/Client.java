package com.tonga.thread.masterwork;

import java.util.Map;
import java.util.Set;

/**
 * Master-Worker模式是常用的并行设计模式。它的核心思想是，系统有两个进程协议工作：Master进程和Worker进程。
 * Master进程负责接收和分配任务，Worker进程负责处理子任务。当各个Worker进程将子任务处理完后，将结果返回给Master进程，由Master进行归纳和汇总，从而得到系统结果。
 * @author tangjia
 *
 */
public class Client {
	
	/**
	 * Master-Worker模式的好处是，它能将大任务分解成若干个小任务，并发执行，从而提高系统性能。
	 * 而对于系统请求者Client来说，任务一旦提交，Master进程就会立刻分配任务并立即返回，并不会等系统处理完全部任务再返回，其处理过程是异步的。
	 * 
	 */
    public static void main(String[] args) {
        Master m = new Master(new PlusWorker(), 5);//启动五个线程处理
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
                re+=i;//并行计算结果集
            }
            
            if(key!=null){
                resultMap.remove(key);//将计算完成的结果移除
            }
        }
        
        System.out.println(re);
    }
}