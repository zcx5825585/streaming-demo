package com.zcx.redsoft.sparkclient;

import com.rabbitmq.client.*;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.receiver.Receiver;

import java.io.UnsupportedEncodingException;

/**
 * 类说明
 *
 * @author zcx
 * @version 创建时间：2018/11/28  15:04
 */
public class MyReceiver extends Receiver {

    String host = "localhost";
    int port = 9999;
    private final static String QUEUE_NAME = "hello";

    public MyReceiver() {
        super(StorageLevel.MEMORY_AND_DISK_2());
    }

    @Override
    public void onStart() {
        new Thread(() -> {
            receive();
        }).start();
    }

    @Override
    public void onStop() {

    }


    private void receive() {
        try {
            System.out.println("connection");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws UnsupportedEncodingException {
                    String message = new String(body, "UTF-8");
                    store(message);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, consumer);
            // Restart in an attempt to connect again when server is active again
        } catch (Throwable t) {
            // restart if there is any other error
            restart("Error receiving data", t);
        }
    }
}
