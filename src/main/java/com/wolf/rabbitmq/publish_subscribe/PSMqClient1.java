package com.wolf.rabbitmq.publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 */
public class PSMqClient1 {

    private static final String EXCHANGE_NAME = "wolf_queue";

    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //获取队列名
        String queueName = channel.queueDeclare().getQueue();
        //绑定队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(queueName, false, consumer);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(15);
            // 返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
