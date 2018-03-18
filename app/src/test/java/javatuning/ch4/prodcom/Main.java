package javatuning.ch4.prodcom;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Main {

    @Test
    public void main() {
        BlockingQueue<PCData> blockingQueue = new LinkedBlockingQueue<>(Producer.MAX);
        Producer producer = new Producer(blockingQueue);
        Producer producer2 = new Producer(blockingQueue);
        Producer producer3 = new Producer(blockingQueue);

        Customer customer = new Customer(blockingQueue);
        Customer customer2 = new Customer(blockingQueue);
        Customer customer3 = new Customer(blockingQueue);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(producer);
        executor.execute(producer2);
        executor.execute(producer3);

        executor.execute(customer);
        executor.execute(customer2);
        executor.execute(customer3);

        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
