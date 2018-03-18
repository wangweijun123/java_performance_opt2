package com.wangweijun;

/**
 * Created by wangweijun on 2018/3/18.
 */

class SampleSupport2 extends SampleSupport {

    public synchronized void doSomething() {
        System.out.println(Thread.currentThread().getName() + " will execute counter++.");
        startTheCountdown();
        counter++;
    }
}