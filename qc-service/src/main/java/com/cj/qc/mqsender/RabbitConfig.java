package com.cj.qc.mqsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author chenzhaowen
 * @description rabbit队列配置
 * @date 2019/4/18 17:17
 */
@Slf4j
@Configuration
public class RabbitConfig {
  @Resource
  private RabbitTemplate rabbitTemplate;

  /**
   * 定制化amqp模版      可根据需要定制多个
   * 此处为模版类定义 Jackson消息转换器
   * ConfirmCallback接口用于实现消息发送到RabbitMQ交换器后接收ack回调   即消息发送到exchange  ack
   * ReturnCallback接口用于实现消息发送到RabbitMQ 交换器，但无相应队列与交换器绑定时的回调  即消息发送不到任何一个队列中  ack
   *
   * @return the amqp template
   */
  @Bean
  public AmqpTemplate amqpTemplate() {
//          使用jackson 消息转换器
    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    rabbitTemplate.setEncoding("UTF-8");
//        开启returncallback     yml 需要 配置    publisher-returns: true
    rabbitTemplate.setMandatory(true);
    rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
      String correlationId = message.getMessageProperties().getCorrelationId();
      log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
    });
    //        消息确认  yml 需要配置   publisher-returns: true
    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
      if (ack) {
        log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
      } else {
        log.debug("消息发送到exchange失败,原因: {}", cause);
      }
    });
    return rabbitTemplate;
  }

  /**
   * 声明Fanout交换机 支持持久化.
   * @return the exchange
   */
  @Bean("fanout.exchange.task.auto")
  public FanoutExchange fanoutExchangeByTaskAuto() {
    return (FanoutExchange)ExchangeBuilder.fanoutExchange("fanout.exchange.task.auto").durable(true).build();
  }

  /**
   * 声明一个队列 支持持久化.
   * @return the queue
   */
  @Bean("fanout.queue.task.auto")
  public Queue fanoutQueueByTaskAuto() {
    return QueueBuilder.durable("fanout.queue.task.auto").build();
  }

  /**
   * 将指定队列绑定到一个指定的交换机 .
   *
   * @param queue    the queue
   * @param fanoutExchange the exchange
   * @return the binding
   */
  @Bean
  public Binding fanoutBindingByTaskAuto(@Qualifier("fanout.queue.task.auto") Queue queue, @Qualifier("fanout.exchange.task.auto") FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(queue).to(fanoutExchange);
  }

  /**
   * 声明Fanout交换机 支持持久化.
   * @return the exchange
   */
  @Bean("fanout.exchange.task.hand")
  public FanoutExchange fanoutExchangeByTaskHand() {
    return (FanoutExchange)ExchangeBuilder.fanoutExchange("fanout.exchange.task.hand").durable(true).build();
  }

  /**
   * 声明一个队列 支持持久化.
   * @return the queue
   */
  @Bean("fanout.queue.task.hand")
  public Queue fanoutQueueByTaskHand() {
    return QueueBuilder.durable("fanout.queue.task.hand").build();
  }

  /**
   * 将指定队列绑定到一个指定的交换机 .
   *
   * @param queue    the queue
   * @param fanoutExchange the exchange
   * @return the binding
   */
  @Bean
  public Binding fanoutBindingByTaskHand(@Qualifier("fanout.queue.task.hand") Queue queue, @Qualifier("fanout.exchange.task.hand") FanoutExchange fanoutExchange) {
    return BindingBuilder.bind(queue).to(fanoutExchange);
  }


}
