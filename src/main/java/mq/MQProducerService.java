package mq;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MQProducerService {

	private static final Logger logger = LoggerFactory.getLogger(MQProducerService.class);

	@Autowired
	@Qualifier("sendTemplate")
	private AmqpTemplate sendTemplate;

	@Value("${mq.send.exchange}")
	private String exchange;

	@Value("${mq.send.queue}")
	private String queue;

	@PostConstruct
	public void afterConstruct() {
		logger.debug("======mq======");
		logger.debug("mq.send.exchange:" + exchange);
		logger.debug("mq.send.queue:" + queue);
		logger.debug("======mq======");
	}

	private void sendQueue(String exchange_key, String queue_key, Object object) {
		sendTemplate.convertAndSend(exchange_key, queue_key, object);
	}

	/**
	 * 发送充值通知到 mq
	 * @param orderNo 订单号
	 * @param userId 用户id
	 */
	public void sendRechargePush(String orderNo, String userId) {
		logger.debug("mq.send.exchange:" + exchange);
		logger.debug("mq.send.queue:" + queue);
		logger.debug("发送信息到 mq:" + "");
		sendQueue(exchange, queue, "");
	}
}
