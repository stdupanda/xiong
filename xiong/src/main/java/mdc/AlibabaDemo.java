package mdc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.threadpool.TtlExecutors;

public class AlibabaDemo{
    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        // 修饰 callable，同理也可修饰 runable
        TtlCallable<Integer> callable = TtlCallable.get(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        service.submit(callable);
        // 修饰 ExecutorService
        service= TtlExecutors.getTtlExecutorService(service);
        // 甚至通过设置 javaagent 实现无代码侵入
    }
}
