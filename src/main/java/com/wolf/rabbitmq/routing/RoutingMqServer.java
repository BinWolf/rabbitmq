package com.wolf.rabbitmq.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/18.
 *
 * 一对多,发布端通过路由把发布的消息推送到绑定在交换机各个消费者自己虚拟的信息队列中实现指定类型的信息都能推送到特殊的订阅者端
 */
public class RoutingMqServer {

    private static final String EXCHANGE_NAME = "routing_exchange_name";

    public static void main(String[] args) throws Exception {
        //获取链接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();

        //定义交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String message = "Hello routing consumer";
        //第二个参数是指定给哪个key订阅者发布消息
        channel.basicPublish(EXCHANGE_NAME, "key1", null, message.getBytes());

        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}
