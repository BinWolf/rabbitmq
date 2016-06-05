package com.wolf.rabbitmq.springrabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * Created by wolf on 16/6/5.
 */
public class QueueTestLitener implements MessageListener {
    public void onMessage(Message message) {
        System.out.println("data : " + new String(message.getBody()));
    }
}
