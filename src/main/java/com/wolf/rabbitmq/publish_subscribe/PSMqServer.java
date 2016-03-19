package com.wolf.rabbitmq.publish_subscribe;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 *
 * 一对多,发布端通过交换机把消息推送到各个消费者自己虚拟的信息队列中实现每一个信息都能推送到订阅者端
 */
public class PSMqServer {
    private static final String EXCHANGE_NAME = "wolf_queue";

    public static void main(String[] args) throws Exception {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        //发布
        for (int i = 0; i < 100; i++) {
            // 消息内容
            String message = "" + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

            Thread.sleep(500);
        }

        channel.close();
        connection.close();
    }
}
