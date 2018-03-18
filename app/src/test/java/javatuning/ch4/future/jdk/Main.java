package javatuning.ch4.future.jdk;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by wangweijun on 2018/3/18.
 */

public class Main {

    @Test
    public void testJDKFuture() {
        FutureTask<String> futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder result = new StringBuilder();
                for (int i=0; i<50; i++) {
                    result.append(i).append(',');
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("call finished "+Thread.currentThread().getId());
                return result.toString();
            }
        });

        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);

        try {
            System.out.println("get "+Thread.currentThread().getId());
            String result = futureTask.get();
            System.out.println("result:"+result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testJDKFutures() {
        Callable<String> c1 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder result = new StringBuilder();
                for (int i=0; i<50; i++) {
                    result.append(i).append(',');
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("call finished "+Thread.currentThread().getId());
                return result.toString();
            }
        };

        Callable<String> c2 = new Callable<String>() {
            @Override
            public String call() throws Exception {
                StringBuilder result = new StringBuilder();
                for (int i=0; i<30; i++) {
                    result.append(i).append(',');
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("call finished "+Thread.currentThread().getId());
                return result.toString();
            }
        };

        List<Callable<String>> callables = new ArrayList<>();
        callables.add(c1);
        callables.add(c2);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<Future<String>> results = executorService.invokeAll(callables);
            for (int i=0; i<results.size(); i++){
                // 注意组赛了哦
                String result = results.get(i).get();
                System.out.println("result:"+result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    final  static int count = 4;
    final  static  int[] arr = {0, 1, 2, 3,4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    @Test
    public void testJDKFutures2() {
        List<Callable<Integer>> callables = new ArrayList<>();
        for (int i=0; i<4; i++) {
            final int index = i;
            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int re = arr[index*count] + arr[index*count+1]+ arr[index*count+2]+ arr[index*count+3];
                    System.out.println("call finished "+Thread.currentThread().getId());
                    return re;
                }
            };
            callables.add(callable);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            int re = 0;
            List<Future<Integer>> results = executorService.invokeAll(callables);
            for (int i=0; i<results.size(); i++){
                // 注意组赛了哦
                Integer result = results.get(i).get();
                System.out.println("result:"+result);
                re = re + result;
            }
            System.out.println("call re:"+re);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJDKFutures3() {
        int re = 0;
        for (int i=0;i<arr.length;i++){
            re += arr[i];
        }
        System.out.println("testJDKFutures3 re:"+re);
    }

}
