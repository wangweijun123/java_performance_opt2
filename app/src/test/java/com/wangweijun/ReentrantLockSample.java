package com.wangweijun;

import org.junit.Test;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class ReentrantLockSample {

    @Test
    public void main() {
//        testSynchronized();
        testReentrantLock();
    }

    public static void testReentrantLock() {
        final SampleSupport1 support = new SampleSupport1();
        Thread first = new Thread(new Runnable() {
            public void run() {
                try {
                    support.doSomething();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"first");

        Thread second = new Thread(new Runnable() {
            public void run() {
                try {
                    support.doSomething();
                }
                catch (InterruptedException e) {
                    System.out.println("Second Thread Interrupted without executing counter++,beacuse it waits a long time.");
                }
            }
        },"second");

        executeTest(first, second);
    }

    public static void testSynchronized() {
        final SampleSupport2 support2 = new SampleSupport2();

        Runnable runnable = new Runnable() {
            public void run() {
                support2.doSomething();
            }
        };

        Thread third = new Thread(runnable,"third");
        Thread fourth = new Thread(runnable,"fourth");

        executeTest(third, fourth);
    }

    /**
     * Make thread a run faster than thread b,
     * then thread b will be interruted after about 1s.
     * @param a
     * @param b
     */
    public static void executeTest(Thread a, Thread b) {
        a.start();
        try {
            Thread.sleep(100);
            b.start();
            Thread.sleep(1000);
            b.interrupt();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
