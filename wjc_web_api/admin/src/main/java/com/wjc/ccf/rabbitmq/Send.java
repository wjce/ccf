package com.wjc.ccf.rabbitmq;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class Send {

    private final static String Queue_Name = "hello world";

//    public static void main(String[] args) throws IOException, TimeoutException {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        connectionFactory.setHost("localhost");
//        Connection connection = connectionFactory.newConnection();
//        Channel channel = connection.createChannel();
//        channel.queueDeclare(Queue_Name, false, false, false, null);
//        String message = getMessage(args);
//        channel.basicPublish("", Queue_Name, null, message.getBytes());
//        System.out.println("message:" + message);
//        channel.close();
//        connection.close();
//        HashMap map = new HashMap();
//
//    }

    private static String getMessage(String[] strings){
        if(strings.length < 1){
            return "e";
        }
        return joinStrings(strings, "");
    }

    private static String joinStrings(String[] strings, String delimter){
        int length = strings.length;
        if(length==0){
            return "";
        }
        StringBuilder sb = new StringBuilder(strings[0]);
        for(int i=1; i<length; i++){
            sb.append(delimter).append(strings[i]);
        }

        return sb.toString();
    }
}
