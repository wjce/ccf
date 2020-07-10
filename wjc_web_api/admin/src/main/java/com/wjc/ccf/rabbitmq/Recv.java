package com.wjc.ccf.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private  final  static String QUEUE_NAME = "hello world" ;

    public static void main(String[] args) throws TimeoutException, IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public  void  handleDelivery (String consumerTag,Envelope envelope,
            AMQP.BasicProperties properties,byte [] body)
                    throws  IOException {
                String message = new String(body,"UTF-8");
                System.out.println("[x] Received'" + message + "'");
                try{
                    doWork(message);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    System.out.println("完成");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }

    private static void doWork(String task)throws InterruptedException{
        for(char ch : task.toCharArray()){
            if(ch == '.'){
                Thread.sleep(1000);
            }
        }
    }

}
