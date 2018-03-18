package javatuning.ch4.future.pattern;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Client {


    public Data query(String a) {
       final FutureData futureData = new FutureData();

        new Thread(new Runnable() {
            @Override
            public void run() {
                RealData realData = new RealData();
                System.out.println("构造真实数据完毕");
                futureData.setRealData(realData);
            }
        }).start();

        return futureData;
    }
}
