package redis分布式锁setNX;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import cn.xz.web.RedisBaseDao;

@Repository
public class RedisLockDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockDao.class);
    private static final long TIME_OUT_SECONDS = 3;

    @Autowired
    @Qualifier(value = "redisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisBaseDao<Long> redisBaseDao;

    public boolean setNX(String key, long timeout, TimeUnit timeUnit) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            return doSetNX(key, timeout, timeUnit);
        } catch (Exception e) {
            LOGGER.error("setNX error!", e);
            return false;
        } finally {
            lock.unlock();
        }
    }

    private boolean doSetNX(String key, long timeout, TimeUnit timeUnit) {
        long begin = System.nanoTime();
        long lockTimeout = TIME_OUT_SECONDS * 1000 * 1000 * 1000;
        long period;
        RedisConnection connection;
        try {
            connection = redisTemplate.getConnectionFactory().getConnection();
        } catch (Exception e) {
            LOGGER.error("get redis connection error!", e);
            return false;
        }

        if (null == connection) {
            LOGGER.error("got null redis connection !");
            return false;
        }

        LOGGER.debug("got redis connection:{}", connection.toString());
        while ((period = System.nanoTime() - begin) < lockTimeout) {
            Boolean lockResult = connection.setNX(key.getBytes(), String.valueOf(System.nanoTime()).getBytes());
            // setNX 为 1
            if (lockResult) {
                LOGGER.debug("setNX ok! cost: {} ns", period);
                redisTemplate.expire(key, timeout, timeUnit);
                LOGGER.debug("set expire ok!");
                close(connection);
                return true;
            }
            // setNX 为 0 ，随机 sleep，然后重试 setNX
            try {
                int i = new Random().nextInt(100);
                Thread.sleep(i);
            } catch (InterruptedException e) {
                LOGGER.error("sleep error!", e);
            }
        }
        // setNX 失败
        try {
            Long s = redisBaseDao.get(key);
            long tmp;
            long timeoutNano = timeUnit.toNanos(timeout);
            if ((tmp = s - System.nanoTime()) > timeoutNano) {
                LOGGER.debug("old val:{}", s);
                LOGGER.info("key: {} has expired for {} ns.", key, tmp);
                // 锁已超时
                redisTemplate.delete(key);
                LOGGER.debug("key deleted.");
            }
        } catch (Exception e) {
            LOGGER.error("check key expire error!", e);
        }
        close(connection);
        LOGGER.error("setNX failed ! cost: {} ", period);
        return false;
    }

    private void close(RedisConnection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            LOGGER.error("close redis connection error!");
        }
    }
}
