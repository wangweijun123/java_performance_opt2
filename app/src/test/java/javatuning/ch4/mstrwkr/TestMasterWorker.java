package javatuning.ch4.mstrwkr;

import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class TestMasterWorker {

    public static final int COUNT = 1000;

    @Test
    public void testMasterWorker() {
        long start = System.currentTimeMillis();
        Master master = new Master();
        for (int i = 0; i < COUNT; i++) {
            master.addTask(i);
        }

        master.start();
        int re = 0;
        Map<String, Object> resultMap = master.getResultMap();
        while (resultMap.size() > 0 || !master.isComplete()) {
            Iterator<Map.Entry<String, Object>> iter = resultMap.entrySet().iterator();
            if (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                re = re + (Integer) entry.getValue();
                resultMap.remove(entry.getKey());
//                System.out.println(Thread.currentThread().getName() + "处理了立方和: " + input);
            }
        }
        // 结果:392146832 , spend time:137
        System.out.println("结果:" + re + " , spend time:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void testPlus() {
        long start = System.currentTimeMillis();
        int re = 0;
        for (int i = 0; i < COUNT; i++) {
            re += i * i * i;
        }
        // testPlus:392146832 , spend time:0
        System.out.println("testPlus:" + re + " , spend time:" + (System.currentTimeMillis() - start));
    }


    @Test
    public void testThreadPool() {
        long start = System.currentTimeMillis();

        /**
         * int corePoolSize,
         int maximumPoolSize,
         long keepAliveTime,
         TimeUnit unit,
         BlockingQueue<Runnable> workQueue
         */
        ExecutorService executor = new ThreadPoolExecutor(2, 4, 5
                , TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        Map<String, Integer> result = new ConcurrentHashMap<String, Integer>();
        for (int i = 0; i < COUNT; i++) {
//            executor.execute(new Task(i, result));
            executor.submit(new Task(i, result));
        }



//        int re = 0;
//        while (result.size() > 0 || !executor.isTerminated()) {
//            System.out.println("size:"+result.size()+", isTerminated :" + executor.isTerminated());
//            Iterator<Map.Entry<String, Integer>> iter = result.entrySet().iterator();
//            if (iter.hasNext()) {
//                Map.Entry<String, Integer> entry = iter.next();
//                re = re + entry.getValue();
//                iter.remove();
//            }
//
//        }
//        System.out.println("testThreadPool:" + re + " , spend time:" + (System.currentTimeMillis() - start));

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int re = 0;
//        Iterator<Map.Entry<String, Integer>> iter = result.entrySet().iterator();
//        while (iter.hasNext()) {
//            Map.Entry<String, Integer> entry = iter.next();
//            re = re + entry.getValue();
//        }
//        // testThreadPool:392146832 , spend time:3009
//        System.out.println("testThreadPool:" + re + " , spend time:" + (System.currentTimeMillis() - start));


    }

    class Task implements Runnable {
        int number;
        Map<String, Integer> result;

        public Task(int number, Map<String, Integer> result) {
            this.number = number;
            this.result = result;
        }

        @Override
        public void run() {
            int re = number * number * number;
            result.put(String.valueOf(number), re);
            System.out.println("计算完:"+number);
        }
    }
}
