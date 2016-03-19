package com.wolf.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 */
public class TopicMqClient1 {

    public static final String QUEUE_NAME = "topic_queue_name2";
    private static final String EXCHANGE_NAME = "topic_exchange_name";

    public static void main(String[] args) throws  Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //绑定队列到交换机,第三个参数指定只订阅哪种消息
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "#.#");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        // 监听队列，手动返回完成
        channel.basicConsume(QUEUE_NAME, false, consumer);
        System.out.println(QUEUE_NAME + " Waiting for messages. To exit press CTRL+C");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [x] Received '" + message + "'");
            Thread.sleep(10);
            // 返回确认状态
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }
}
