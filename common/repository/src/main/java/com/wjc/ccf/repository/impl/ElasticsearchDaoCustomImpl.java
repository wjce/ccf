package com.wjc.ccf.repository.impl;

import com.wjc.ccf.repository.custom.ElasticsearchDaoCustom;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;

import static org.elasticsearch.action.support.WriteRequest.RefreshPolicy.IMMEDIATE;

/**
 * @author wjc
 * @description
 * @date 2020/4/16
 */
public class ElasticsearchDaoCustomImpl implements ElasticsearchDaoCustom {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public void test() {
        RestClient restClient = restHighLevelClient.getLowLevelClient();

//        RequestOptions requestOptions = new RequestOptions(RequestOptions.Builder());
//        IndexRequest request = new IndexRequest("spring-data", "elasticsearch", randomID())
//                .source(singletonMap("feature", "high-level-rest-client"))
//                .setRefreshPolicy(IMMEDIATE);
//
//        IndexResponse response = restHighLevelClient.index(request);
    }
}
