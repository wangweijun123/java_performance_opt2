package javatuning.ch4.mstrwkr;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wangweijun on 2018/3/18.
 *
 * Master --- Worker工作模式
 *
 * master接到任务后分解为小任务,交给worker死循坏的去执行小任务，得到结果后放入map(注意线程安全)中
 * 如果渠道任务为空，说明queue已无任务退出线程
 *
 * Master 中判断结果集中是否还有数据或者所有线程是否已经全部退出
 *
 */

public class Master {

    // 任务队列
    Queue<Object> workQueue = new LinkedBlockingQueue<>();

    // 结果集
    Map<String, Object> resultMap = new ConcurrentHashMap<>();

    // 线程集合
    Map<String, Thread> threadMap = new HashMap<>();

    public void start() {
        Worker worker = new Worker(workQueue, resultMap);
        for (int i=0; i<5; i++) {
            Thread thread = new Thread(worker);
            threadMap.put(String.valueOf(i), thread);
            thread.start();
        }
    }

    public void addTask(Object job) {
        workQueue.add(job);
    }


    //是否所有的子任务都结束了
    public boolean isComplete(){
        for(Map.Entry<String,Thread> entry:threadMap.entrySet()){
            if(entry.getValue().getState()!=Thread.State.TERMINATED){// terminated终止
                return false;
            }
        }
        return true;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }
}
