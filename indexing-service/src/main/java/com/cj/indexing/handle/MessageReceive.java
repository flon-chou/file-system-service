package com.cj.indexing.handle;

import com.cj.indexing.service.VoiceIndexingService;
import com.cj.mq.MessageEntity;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author : Flon
 * @version V1.0
 * @Description: 接收消息中间件的消息
 * @date Date : 2019/04/09 11:09
 */
@Slf4j
@Component
public class MessageReceive {
	@Value("${index-file-path}")
	private String path;

	@Autowired
	private VoiceIndexingService voiceIndexingService;

	@RabbitListener(
			bindings = @QueueBinding(
					value = @Queue(value = "queue.indexing", durable = "true"),
					exchange = @Exchange(value = "file-handle", durable = "true",type = "fanout"),
					key = "index.created"
			)
	)
	@RabbitHandler
	public void process(@Payload MessageEntity<Boolean> message, @Headers Map<String,Object> headers, Channel channel) throws IOException {
		log.info("received handle:{}"+message.getMsg());
		Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
		channel.basicAck(deliveryTag,false);
		if (message.getT()){
			voiceIndexingService.saveTXT(path);
		}
	}

}
