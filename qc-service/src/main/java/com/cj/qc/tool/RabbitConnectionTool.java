package com.cj.qc.tool;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author chenzhaowen
 * @description RabbitMQ连接工具类
 * @date 2019/4/18 18:08
 */
public class RabbitConnectionTool {
  /**
   * 获取RabbitMQ Connection连接
   * @return
   * @throws IOException
   * @throws TimeoutException
   */
  public static Connection getConnection(String host,int port,String username,String password) throws IOException, TimeoutException {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(host);
    connectionFactory.setPort(port);
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    return connectionFactory.newConnection();
  }

}
