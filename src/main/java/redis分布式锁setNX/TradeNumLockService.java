package redis分布式锁setNX;

/**
 * 订单号验证
 */
public interface TradeNumLockService {
    /**
     * 验证订单号是否已缓存在 Redis
     *
     * @param tradeNum 订单号
     * @return 此订单号是否已缓存在 Redis
     */
    boolean checkTradeNum(String tradeNum);
}
