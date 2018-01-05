package mq;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("MQConsumerService")
public class MQConsumerService implements MessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MQConsumerService.class);

	private MQProducerService mqProducerService;

	@Autowired
	public MQConsumerService(MQProducerService mqProducerService) {
		Assert.notNull(mqProducerService, "must not be null!");
		this.mqProducerService = mqProducerService;
	}

	@PostConstruct
	public void afterConstruct() {
		logger.info("mq 消息处理任务初始化完成.");
	}

	@Override
	public void onMessage(Message message) {
		String req = message.toString();
		logger.debug("收到 mq 推送: " + req);
		String body = new String(message.getBody());
		logger.debug("mq 推送解析: " + body);
		mqProducerService.sendRechargePush("", "");
	}
}
