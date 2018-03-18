package javatuning.ch4.mstrwkr;

import java.util.Map;
import java.util.Queue;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Worker implements Runnable {

    Queue<Object> workQueue;// 注意线程安全

    Map<String, Object> resultMap;// 注意线程安全数据结构,key是线程索引

    public Worker(Queue<Object> workQueue, Map<String, Object> resultMap) {
        this.workQueue = workQueue;
        this.resultMap = resultMap;
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "开始工作");
        while (true) {
            // 不断从任务队列取任务
            Object input = workQueue.poll();
            if (input == null) {
                System.out.println(Thread.currentThread().getName() + "线程退出了");
                break;
            }
            int re = handle(input);
            System.out.println(Thread.currentThread().getName() + "处理了立方和: " + input);
            // 写入结果集
            resultMap.put(String.valueOf(input), re);
        }
    }

    public int handle(Object input) {
        Integer i = (Integer)input;
        int re = i * i * i;
        return re;
    }
}
