package com.cj.qc.mqreceiver;

import cn.hutool.core.thread.ThreadUtil;
import com.cj.qc.fastmybatismapper.TaskQueueAutoMapper;
import com.cj.qc.service.TaskEngineService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author chenzhaowen
 * @description Rabbit监听器
 * @date 2019/4/18 17:37
 */
@Slf4j
@Component
public class RabbitReceiver {

  @Resource
  private TaskEngineService taskEngineService;

  /**
   * fanout模式.
   * 自动质检任务队列消费
   * @param message the message
   * @param channel the channel
   * @throws IOException the io exception  这里异常需要处理
   */
  @RabbitListener(queues = {"fanout.queue.task.auto"})
  public void consumeTaskQueueAuto(Message message, Channel channel) throws IOException {
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    System.out.println("-----------------------------[监听消息]自动质检任务队列消费：----------"+new String (message.getBody()));
    log.info("自动质检任务队列消费：{}",new String (message.getBody()));

    //执行每日质检任务
    taskEngineService.executeAutoTaskForDay(new String (message.getBody()));

    //todo:可能后期需要返回执行成功信息给我的消息
  }

  /**
   * fanout模式.
   * 手动质检任务队列消费
   * @param message the message
   * @param channel the channel
   * @throws IOException the io exception  这里异常需要处理
   */
  @RabbitListener(queues = {"fanout.queue.task.hand"})
  public void consumeTaskQueueHand(Message message, Channel channel) throws IOException {
    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    System.out.println("-----------------------------[监听消息]手动质检任务队列消费：----------"+new String (message.getBody()));
    log.info("手动质检任务队列消费：任务id={}",new String (message.getBody()));

    //执行单个手动质检任务
    taskEngineService.executeSingleHandTask(Long.valueOf(new String (message.getBody())));

    //todo:可能后期需要返回执行成功信息给我的消息
  }
}
