package javatuning.ch4.future.pattern;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class FutureData implements Data{
    RealData realData;
    boolean isReady;
    @Override
    public synchronized String getResult() {
        while (!isReady) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return realData.getResult();
    }

    public synchronized void setRealData(RealData realData) {
        this.realData = realData;
        isReady = true;
        notifyAll();
    }
}
