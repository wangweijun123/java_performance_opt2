package javatuning.ch4.prodcom;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Customer implements Runnable {

    private BlockingQueue<PCData> blockingQueue;

    private volatile boolean isRunning = true;

    private Random random = new Random();

    public Customer(BlockingQueue<PCData> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        try {
            while (isRunning) {
                PCData data = blockingQueue.take();
                if (data != null) {
                    int re = random.nextInt(5);
                    System.out.println(Thread.currentThread().getId() + " 线程消费了:" + data.getData()+", queue size:"+blockingQueue.size()+ "，休息秒:"+re);
                    try {
                        Thread.sleep(re * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        isRunning = false;
                    }
                }
            }
            System.out.println(Thread.currentThread().getId() + " 消费线程stop");
        } catch (Exception e) {
            e.printStackTrace();
            isRunning = false;
            System.out.println(Thread.currentThread().getId() + " 消费线程stop");
        }


    }

    public void stop() {
        isRunning = false;
    }
}
