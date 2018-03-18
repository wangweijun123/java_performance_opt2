package javatuning.ch4.future.pattern;

import org.junit.Test;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Main {

    @Test
    public void testFuture() {
        Client client = new Client();
        System.out.println("client query");
        Data data = client.query("a");
        System.out.println("call getResult");
        String res = data.getResult();
        System.out.println("res:"+res);
    }
}
