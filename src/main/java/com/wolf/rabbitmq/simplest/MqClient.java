package com.wolf.rabbitmq.simplest;

import com.rabbitmq.client.*;
import com.wolf.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by wolf on 16/3/18.
 */
public class MqClient {

    private static final String QUEUE_NAME = "wolf_queue";

    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
