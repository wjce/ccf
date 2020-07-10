package com.wjc.ccf.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

public class RpcServer {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

//    //斐波拉契任务
//    private static int fib(int i){
//        System.out.println(i);
//        if(i == 0){
//            return 0;
//        }
//
//        if(i == 1){
//            return 1;
//        }
//
//        return fib(i-1) + fib(i-2);
//    }

    public static void main(String[] args) {
        Connection connection = null;
        //消息通道，每个channel代表一个会话任务
        Channel channel = null;

        try{
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("localhost");

            connection = connectionFactory.newConnection();
            channel = connection.createChannel();

            //b为true时消息会持久化
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            //每次只接收一条消息
            channel.basicQos(1);

            channel.basicCancel(RPC_QUEUE_NAME );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
