package com.wjc.ccf.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Configuration
public class ESExistsListener {
    @Bean("existsListener")
    public ActionListener listener(){
        ActionListener<Boolean> listener = new ActionListener<Boolean>() {
            @Override
            public void onResponse(Boolean exists) {

            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        return listener;
    }
}
