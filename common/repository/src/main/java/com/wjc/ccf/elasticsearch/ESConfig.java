//package com.wjc.ccf.elasticsearch;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//
///**
// * @author wjc
// * @description spring data es
// * @date 2020/4/16
// */
//@Configuration
//public class ESConfig {
//
//    @Bean
//    public RestHighLevelClient client(){
//        ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("111.231.55.74:9200").build();
//        return RestClients.create(clientConfiguration).rest();
//    }
//
//
//}
