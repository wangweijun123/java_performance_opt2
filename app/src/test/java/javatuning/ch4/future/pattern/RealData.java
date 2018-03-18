package javatuning.ch4.future.pattern;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class RealData implements Data{
    StringBuilder result;
    public RealData() {
        result = new StringBuilder();
        for (int i=0; i<50; i++) {
            result.append(i).append(',');
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getResult() {
        return result.toString();
    }
}
