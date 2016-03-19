package com.wolf.rabbitmq.simplest;

import com.rabbitmq.client.*;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 *
 * 一对一消息发送
 *
 * P ---->[|||||]----->C
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
        String message = "Hello wolf";
        //发布
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Sent '" + message + "'");

        channel.close();

        connection.close();
    }
}
