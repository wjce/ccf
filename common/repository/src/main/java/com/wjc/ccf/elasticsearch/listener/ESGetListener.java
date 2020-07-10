package com.wjc.ccf.elasticsearch.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Slf4j
@Configuration
public class ESGetListener {
    @Bean("getListener")
    public ActionListener listener(){
        ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
            //在执行成功完成时调用。
            @Override
            public void onResponse(GetResponse getResponse) {
                String index = getResponse.getIndex();
                String type = getResponse.getType();
                String id = getResponse.getId();
                if (getResponse.isExists()) {
                    long version = getResponse.getVersion();
                    //以字符串的形式检索文档
                    String sourceAsString = getResponse.getSourceAsString();
                    //以Map<String, Object>的形式检索文档
                    Map<String, Object> sourceAsMap = getResponse.getSourceAsMap();
                    //以字节[]的形式检索文档
                    byte[] sourceAsBytes = getResponse.getSourceAsBytes();
                    log.info("jsonString:  " + sourceAsString);
                    log.info("map:  " + JSON.toJSONString(sourceAsMap));
                    log.info("byte[]:   " + new String(sourceAsBytes));
                } else {
                    //处理没有找到文档的场景。注意，虽然返回的响应有404状态代码，但是返回的是有效的GetResponse，
                    // 而不是抛出异常。这种响应不包含任何源文档，其isExists方法返回false。
                }
            }

            //当整个GetRequest失败时调用。
            @Override
            public void onFailure(Exception e) {

            }
        };
        return listener;
    }
}
