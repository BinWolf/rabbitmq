package com.wolf.rabbitmq.publish_subscribe;

import com.rabbitmq.client.*;
import com.wolf.rabbitmq.util.ConnectionUtil;

import java.io.IOException;

/**
 * Created by wolf on 16/3/18.
 */
public class PSMqClient {

    private static final String EXCHANGE_NAME = "wolf_queue";

    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取队列名
        String queueName = channel.queueDeclare().getQueue();
        //绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(queueName, false, consumer);
    }
}
