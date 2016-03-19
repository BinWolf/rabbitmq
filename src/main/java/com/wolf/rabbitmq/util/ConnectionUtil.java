package com.wolf.rabbitmq.util;

import com.rabbitmq.client.*;

/**
 * Created by wolf on 16/3/17.
 */
public class ConnectionUtil {

    public static Connection getConnection() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/wolf");
        factory.setUsername("wolf");
        factory.setPassword("wolf");

        Connection connection = factory.newConnection();

        return connection;
    }

}
