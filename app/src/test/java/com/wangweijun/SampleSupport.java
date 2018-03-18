package com.wangweijun;

/**
 * Created by wangweijun on 2018/3/18.
 */

abstract class SampleSupport {

    protected int counter;

    /**
     * A simple countdown,it will stop after about 5s.
     */
    public void startTheCountdown() {
        long currentTime = System.currentTimeMillis();
        for (;;) {
            long diff = System.currentTimeMillis() - currentTime;
            if (diff > 5000) {
                break;
            }
        }
    }
}
