package com.zcx.redsoft.socketserver;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 类说明
 *
 * @author zcx
 * @version 创建时间：2018/11/28  15:32
 */
public class MQSender {
    private final static String QUEUE_NAME = "hello";
    Connection connection;
    Channel channel;

    public MQSender() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        try {
            String message = msg;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        channel.close();
        connection.close();
    }

    public void sendByStep(String msg, int time, int step) {
        new Thread(() -> {
            for (int i = 0; i < time; i++) {
                send(msg);
                try {
                    Thread.sleep(step);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
