package com.wolf.rabbitmq.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.*;
import com.wolf.rabbitmq.util.ConnectionUtil;

import java.util.UUID;

/**
 * Created by wolf on 16/3/19.
 */
public class RPCClient {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_name";
    private String replyQueueName;
    private QueueingConsumer consumer;

    public RPCClient() throws  Exception {
        connection = ConnectionUtil.getConnection();
        channel = connection.createChannel();

        replyQueueName = channel.queueDeclare().getQueue();
        consumer = new QueueingConsumer(channel);
        channel.basicConsume(replyQueueName, true, consumer);
    }

    public String call(String message) throws Exception {
        String response = null;
        String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                                            .Builder()
                                            .correlationId(corrId)
                                            .replyTo(replyQueueName)
                                            .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes());

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                response = new String(delivery.getBody());
                break;
            }
        }

        return response;
    }

    public void close() throws Exception {
        connection.close();
    }

    public static void main(String[] args) throws Exception{
        RPCClient client = new RPCClient();
        String response = client.call("4");
        System.out.println("received : " + response);

        client.close();
    }
}
