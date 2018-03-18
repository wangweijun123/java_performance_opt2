package javatuning.ch4.prodcom;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Producer implements Runnable {
    public static final int MAX = 10;

    public static AtomicInteger count = new AtomicInteger();

    private BlockingQueue<PCData> blockingQueue;


    private volatile boolean isRunning = true;

    private Random random = new Random();

    public Producer(BlockingQueue<PCData> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (isRunning) {
            PCData pcData = new PCData(count.getAndIncrement());
            blockingQueue.offer(pcData);
            int re = random.nextInt(5);
            System.out.println(Thread.currentThread().getId() + " 线程生产了:"+pcData.getData()+", queue size:"+blockingQueue.size()+", 休息秒:"+re);
            try {
                Thread.sleep(re * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isRunning = false;
            }
            if (blockingQueue.size() == MAX) {
                isRunning = false;
            }
        }
        System.out.println(Thread.currentThread().getId() + " 生产线程stop");
    }

    public void stop() {
        isRunning = false;
    }
}
