package com.wolf.rabbitmq.springrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by wolf on 16/6/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring*.xml"})
public class RabbiMQTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void testSend() throws InterruptedException {
        while (true){
            System.out.println("send start ....");
            amqpTemplate.convertAndSend("queue_one_key", "Hello wolf ....");
            System.out.println("send end ....");
        }

    }

    @Test
    public void testReceive(){
        while (true){
            System.out.println("receive start ....");
            amqpTemplate.receiveAndConvert("queue_one");
            System.out.println("receive end ....");
        }
    }
}
