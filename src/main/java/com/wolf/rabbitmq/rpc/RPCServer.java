package com.wolf.rabbitmq.rpc;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;
import com.wolf.rabbitmq.util.ConnectionUtil;

/**
 * Created by wolf on 16/3/19.
 *
 * PRC消息队列
 */
public class RPCServer {

    private static final String RPC_QUEUE_NAME = "rpc_name";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(RPC_QUEUE_NAME, false, consumer);

        System.out.println(" [x] Awaiting RPC requests");

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();

            BasicProperties props = delivery.getProperties();
            BasicProperties replyProps = new BasicProperties
                                            .Builder()
                                            .correlationId(props.getCorrelationId())
                                            .build();

            String message = new String(delivery.getBody());
            int n = Integer.parseInt(message);

            System.out.println(" [.] fib(" + message + ")");
            String response = "" + fib(n);
            channel.basicPublish("", props.getReplyTo(), replyProps, response.getBytes());

            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }
    }

    /**
     * 斐波那契数列问题(1,1,2,3,5,8,13,21这样的数列，从第三个数开始每个数等于它前面的两个数相加)
     * @param n
     * @return
     */
    private static int fib(int n) {
        if(n == 0 ) return 0;
        if(n == 1 ) return 1;
        return fib(n - 1) + fib(n - 2);
    }

}
