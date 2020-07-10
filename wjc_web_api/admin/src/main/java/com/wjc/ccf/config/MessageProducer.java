//package com.wjc.ccf.config;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author wjc
// * @description
// * @date 2019/8/9
// */
//@Component
//public class MessageProducer {
//    @Autowired
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendMessage(String str) {
//
//        rabbitTemplate.convertAndSend("directExchange", "DIRECT_ROUTING_KEY", str);
//    }
//}
