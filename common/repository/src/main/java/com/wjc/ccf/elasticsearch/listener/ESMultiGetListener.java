package com.wjc.ccf.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/11
 */
@Configuration
public class ESMultiGetListener {
    @Bean("multiGetListener")
    public ActionListener listener(){
        ActionListener listener = new ActionListener<MultiGetResponse>() {
            @Override
            public void onResponse(MultiGetResponse response) {
                MultiGetItemResponse firstItem = response.getResponses()[0];
                if(firstItem.getFailure() == null){
                    //返回null代表没有失败
                }else {
                    GetResponse firstGet = firstItem.getResponse();
                    String index = firstItem.getIndex();
                    String type = firstItem.getType();
                    String id = firstItem.getId();
                    if (firstGet.isExists()) {
                        long version = firstGet.getVersion();
                        String sourceAsString = firstGet.getSourceAsString();
                        Map<String, Object> sourceAsMap = firstGet.getSourceAsMap();
                        byte[] sourceAsBytes = firstGet.getSourceAsBytes();
                    } else {
                        //处理没有找到文档的场景。注意，虽然返回的响应有404状态代码，
                        // 但是返回的是有效的GetResponse，而不是抛出异常。这种响应不包含任何源文档，其isExists方法返回false
                    }
                    //当对一个不存在的索引执行的一个子请求时，getFailure将包含一个异常

                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        return listener;
    }
}
