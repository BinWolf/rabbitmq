package com.wolf.rabbitmq.work;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 *
 * 一对多,但是每个消息只能被一个消费者拿到
 */
public class MqServer {
    private static final String QUEUE_NAME = "wolf_queue";

    public static void main(String[] args) throws Exception {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //定义队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //发布
        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(i * 30);
        }

        channel.close();
        connection.close();
    }
}
