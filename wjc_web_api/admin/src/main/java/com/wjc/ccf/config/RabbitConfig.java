package com.wjc.ccf.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    /**
     * 死信队列 交换机标识符
     */
    private static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    private static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    public static final String DEAD_LETTER_EXCHANGE = "TDL_EXCHANGE";
    public static final String DEAD_LETTER_TEST_ROUTING_KEY = "TDL_KEY";
    public static final String DEAD_LETTER_REDIRECT_ROUTING_KEY = "TKEY_R";
    public static final String DEAD_LETTER_QUEUE = "TDL_QUEUE";
    public static final String REDIRECT_QUEUE = "TREDIRECT_QUEUE";
    public static final String DELAYED_QUEUE = "DELAYED_QUEUE";
    public static final String DELAYED_ROUTING_KEY = "DELAYED_ROUTING_KEY";
    public static final String DELAYED_EXCHANGE = "DELAYED_EXCHANGE";

    /**
     *
     */
//    @Autowired(required = false)
//    private RabbitTemplate rabbitTemplate;
//
//    @Bean
//    public AmqpTemplate amqpTemplate(){
//        Logger logger = LoggerFactory.getLogger(RabbitTemplate.class);
//
//        //使用json消息转换
//        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//        rabbitTemplate.setEncoding("UTF-8");
//        //开启returncallback properties需要配置publisher-returns:true
//        rabbitTemplate.setMandatory(true);
//        rabbitTemplate.setReturnCallback((message , replyCode, replyText, exchange, routingKey) -> {
//            String correlationId = message.getMessageProperties().getCorrelationId();
//            logger.debug("消息：{"+correlationId+"} 发送失败, 应答码：{"+replyCode+"} 原因：{"+replyText+"} 交换机: {"+exchange+"}  路由键: {"+routingKey+"}");
//        });
//
//        //消息确认 需要配置 publisher-returns: true
//        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
//            if(ack){
//                logger.debug("消息发送到exchange成功,id: {"+correlationData.getId()+"}");
//            }else {
//                logger.debug("消息发送到exchange失败,原因: {"+cause+"}");
//            }
//        });
//
//        return rabbitTemplate;
//    }
    /** 声明direct交换机  支持持久化  */
    @Bean("directExchange")
    public Exchange directExchange(){
        return ExchangeBuilder.directExchange("DIRECT_EXCHANGE").durable(true).build();
    }

    /** 声明一个队列  支持持久化   */
    @Bean("directQueue")
    public Queue directQueue(){
        return QueueBuilder.durable("DIRECT_QUEUE").build();
    }

    /** 通过绑定键 将指定队列绑定到一个指定的交换机  */
    @Bean
    public Binding directBinding(@Qualifier("directQueue") Queue queue, @Qualifier("directExchange") Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with("DIRECT_ROUTING_KEY").noargs();
    }

    /** 声明fanout交换机 */
    @Bean("fanoutExchange")
    public FanoutExchange fanoutExchange(){
        return (FanoutExchange) ExchangeBuilder.fanoutExchange("FANOUT_EXCHANGE").durable(true).build();
    }

    /** 队列A */
    @Bean("fanoutQueueA")
    public Queue fanoutQueueA(){
        return QueueBuilder.durable("FANOUT_QUEUE_A").build();
    }

    /** 队列B */
    @Bean("fanoutQueueB")
    public Queue fanoutQueueB(){
        return QueueBuilder.durable("FANOUT_QUEUE_B").build();
    }

    /** 绑定队列A到fanout交换机 */
    @Bean
    public Binding bindingA(@Qualifier("fanoutQueueA") Queue queue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /** 绑定队列B到fanout交换机 */
    @Bean
    public Binding bindingB(@Qualifier("fanoutQueueB") Queue queue, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    /** 声明deadLetterExchange交换机    */
    @Bean("deadLetterExchange")
    public Exchange deadLetterExchange(){
        return ExchangeBuilder.directExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }
//
    /** 声明一个死信队列(与交换机无关)    */
    @Bean("deadLetterQueue")
    public Queue deadLetterQueue(){
        Map<String,Object> map = new HashMap<>();
        //声明死信交换机
        map.put(DEAD_LETTER_QUEUE_KEY, DEAD_LETTER_EXCHANGE);
        //声明死信路由键
        map.put(DEAD_LETTER_ROUTING_KEY, DEAD_LETTER_REDIRECT_ROUTING_KEY);
        map.put("x-message-ttl", 5000);
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).withArguments(map).build();
    }
//
    /** 定义死信队列转发队列*/
    @Bean("redirectQueue")
    public Queue redirectQueue(){
        return QueueBuilder.durable(REDIRECT_QUEUE).build();
    }
//
    /** 死信路由通过DL_KEY 绑定键绑定到死信队列上    */
    @Bean
    public Binding deadLetterBinding(){
        return new Binding(DEAD_LETTER_QUEUE, Binding.DestinationType.QUEUE, DEAD_LETTER_EXCHANGE, DEAD_LETTER_TEST_ROUTING_KEY, null);
    }

    /** 死信路由通过 KEY_R 绑定键绑定到死信队列上   */
    @Bean
    public Binding redirectBinding(){
        return new Binding(REDIRECT_QUEUE,Binding.DestinationType.QUEUE, DEAD_LETTER_EXCHANGE, DEAD_LETTER_REDIRECT_ROUTING_KEY, null);
    }

    /**
     * 构建延迟消息交换器
     * @return
     */
    @Bean
    public CustomExchange buildExchange() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(DELAYED_EXCHANGE, "x-delayed-message", true, false, args);
    }

    /**
     * 延时队列
     * @return
     */
    @Bean
    public Queue delayQueue(){
        return new Queue(DELAYED_QUEUE,true);
    }

    /**
     * 给延时队列绑定交换机
     * @return
     */
    @Bean
    public Binding bindingExchage(){
        return BindingBuilder.bind(delayQueue()).to(buildExchange()).with(DELAYED_ROUTING_KEY).noargs();
    }
}
