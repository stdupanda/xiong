package mq;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rabbitmq.client.Channel;

@Service("MQConsumerService2")
public class MQConsumerService2 implements ChannelAwareMessageListener {

	private static final Logger logger = LoggerFactory.getLogger(MQConsumerService2.class);

	private MQProducerService mqProducerService;

	@Autowired
	public MQConsumerService2(MQProducerService mqProducerService) {
		Assert.notNull(mqProducerService, "must not be null!");
		this.mqProducerService = mqProducerService;
	}

	@PostConstruct
	public void afterConstruct() {
		logger.info("mq 消息处理任务初始化完成.");
	}

	@Override
	public void onMessage(Message message, Channel channel) {
	    try {
    		String req = message.toString();
    		logger.debug("收到 mq 推送: " + req);
    		String body = new String(message.getBody());
    		logger.debug("mq 推送解析: " + body);
    		mqProducerService.sendRechargePush("", "");
    		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);// 手动 ack
	    } catch (Exception e) {
	        logger.error("", e);
	        try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);// 拒绝 ack，重归队列
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
	}
}
