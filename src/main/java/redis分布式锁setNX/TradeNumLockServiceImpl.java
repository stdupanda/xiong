package redis分布式锁setNX;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TradeNumLockServiceImpl implements TradeNumLockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeNumLockServiceImpl.class);

    @Autowired
    private RedisLockDao redisLockDao;

    @Override
    public boolean checkTradeNum(String tradeNum) {
    	LOGGER.debug(tradeNum);
        return !redisLockDao.setNX(tradeNum, 2, TimeUnit.MINUTES);// 缓存订单号2分钟（mysql 事务应该可以完成）
    }
}
