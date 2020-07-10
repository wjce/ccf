package com.wjc.ccf.elasticsearch.api.index;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wjc
 * @description
 * @date 2020/5/9
 */
@Component
public class GetApi {

    @Autowired
    private RestHighLevelClient highLevelClient;
    @Autowired
    @Qualifier("getListener")
    private ActionListener listener;


    public String get(String index, String id){
        String message = null;
        GetRequest request = new GetRequest(index,  id);
//        GetRequest request = new GetRequest(index,  id);
        //禁用源检索，默认启用
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        //为特定字段配置源包含
        String[] includes = new String[]{"user", "message", "*Date"};
        //为特定字段配置源排除
        String[] excludes = Strings.EMPTY_ARRAY;
//        String[] excludes = new String[]{"message"};
        FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, excludes);
        request.fetchSourceContext(fetchSourceContext);
//        request.routing("routing");
//        request.preference("preference");
//
//        //为特定的存储字段配置检索(要求字段在映射中单独存储)
//        request.storedFields("message");
//        //将realtime标记设置为false(默认为true)
//        request.realtime(false);
//        //在检索文档之前执行刷新(默认为false)
//        request.refresh(true);
//        request.version(2);
//        request.versionType(VersionType.EXTERNAL);

        try {
            GetResponse getResponse = highLevelClient.get(request, RequestOptions.DEFAULT);
            highLevelClient.getAsync(request, RequestOptions.DEFAULT, listener);
            //检索消息存储字段(要求字段在映射中单独存储)
//            message = getResponse.getField("message").getValue();
            message = getResponse.getSourceAsString();
        }catch (IOException e){

        }//当对不存在的索引执行get请求时，响应有404状态代码，抛出ElasticsearchException，需要按如下方式处理
        catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                //处理因索引不存在而引发的异常
            }
            //如果已请求特定的文档版本，而现有文档具有不同的版本号，则会引发版本冲突
            if (e.status() == RestStatus.CONFLICT) {

            }
        }

        return message;
    }

}
