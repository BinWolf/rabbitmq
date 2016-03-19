package com.wolf.rabbitmq.simplest;

import com.rabbitmq.client.*;
import com.wolf.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by wolf on 16/3/18.
 */
public class MqClient1 {

    private static final String QUEUE_NAME = "wolf_queue";

    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(QUEUE_NAME, true, consumer);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
        }
    }
}
