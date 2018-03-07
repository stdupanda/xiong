package 多线程;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class 测试awaitTermination {

    private static final Logger logger = LoggerFactory.getLogger(测试awaitTermination.class);
    
    @Scheduled(cron="0/2 * * * * ?")
    public void aaa() {
        int workerNum = 10;
        ExecutorService service = Executors.newFixedThreadPool(workerNum );
        logger.info("==============================线程池已创建==============================");
        logger.debug("工作线程数： {}", workerNum);

        int size = 10;
        for (int i = 0; i < size; i++) {
            service.submit(new Runnable() {
                String prefix = "uu";

                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    if (!name.startsWith(prefix)) {
                        Thread.currentThread().setName("utc_" + name);// user trade clear
                    }

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        logger.error(name, e);
                    }
                    logger.debug("ok");
                }
            });
        }

        service.shutdown();
        try {
            service.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            logger.error("", e);
        }
        logger.info("==============================线程池已停止==============================");
    }
}
