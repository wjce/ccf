package com.wjc.ccf.elasticsearch.api.index;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wjc
 * @description
 * @date 2020/5/11
 */
@Component
public class MultiGetApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("multiGetListener")
    private ActionListener listener;

    //multiGet API并行地在一个http请求中执行多个get请求
    public void multiGet(){
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item("index", "example_id")
                .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        request.add(new MultiGetRequest.Item("index", "example_id")
                .storedFields("foo"));

        request.preference("some_preference");
        request.realtime(false);
        request.refresh(true);
        request.add(new MultiGetRequest.Item("index", "with_routing")
                .routing("some_routing"));
        request.add(new MultiGetRequest.Item("index", "with_version")
                .versionType(VersionType.EXTERNAL)
                .version(10123L));

        try {
            MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
            MultiGetItemResponse item = response.getResponses()[0];
            String value = item.getResponse().getField("foo").getValue();
        }catch (IOException e){
            e.printStackTrace();
        }
        client.mgetAsync(request, RequestOptions.DEFAULT, listener);
    }
}
