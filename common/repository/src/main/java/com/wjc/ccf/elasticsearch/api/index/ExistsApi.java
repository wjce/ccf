package com.wjc.ccf.elasticsearch.api.index;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Component
public class ExistsApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("existsListener")
    private ActionListener listener;


    public boolean existis(String index, String type, String id){
        Boolean exists = null;
//        GetRequest getRequest = new GetRequest(index, type, id);
        GetRequest getRequest = new GetRequest("posts", "doc", "1");
        //因为exists()只返回true或false，所以建议关闭抓取_source和所有存储字段，这样请求会更轻一些
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");

        try {
            //同步
            exists = client.exists(getRequest, RequestOptions.DEFAULT);
            //异步
//            client.existsAsync(getRequest, RequestOptions.DEFAULT, listener);
        }catch (IOException e){

        }catch (ElasticsearchException e) {

        }

        return exists;
    }
}
