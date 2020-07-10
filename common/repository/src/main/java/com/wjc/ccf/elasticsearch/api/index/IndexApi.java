package com.wjc.ccf.elasticsearch.api.index;

import com.wjc.ccf.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/9
 */
@Component
public class IndexApi {

    @Autowired
    private RestHighLevelClient highLevelClient;
    @Autowired
    @Qualifier("indexListener")
    private ActionListener listener;

    //添加索引
    //json格式
    public void source(String index, String id, String jsonString, String routing, TimeValue timeValue, WriteRequest.RefreshPolicy policy,
                       Integer version, VersionType versionType, String opType, String pipeline){
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch1\"" +
                "}";

        request.source(jsonString, XContentType.JSON);
        //设置路由
        if(StringUtils.isNotBlank(routing)){
            request.routing("routing");
        }
//        //设置超时
        if(timeValue != null){
            request.timeout(timeValue);
//            request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
        }
//
//        //将策略刷新为WriteRequest。RefreshPolicy实例
        if(policy != null){
            request.setRefreshPolicy(policy);
//            request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        //将策略刷新为字符串
//        request.setRefreshPolicy("wait_for");
        }

        if(version != null){
            request.version(version);
        }
        if(versionType != null){
            request.versionType(VersionType.EXTERNAL);
        }
//
//        //作为DocWriteRequest提供的操作类型。OpType价值
//        request.opType(DocWriteRequest.OpType.CREATE);
//        //以字符串形式提供的操作类型:可以创建或索引(默认)
        if(StringUtils.isNotBlank(opType)){
            request.opType("create");
        }
//
//        //索引文档之前要执行的摄取管道的名称
        if(StringUtils.isNotBlank(pipeline)){
            request.setPipeline(pipeline);
        }

        //同步 需创建索引后执行后续代码
        try {
            highLevelClient.index(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ElasticsearchException e){
            if (e.status() == RestStatus.CONFLICT) {
                //引发的异常表明返回了版本冲突错误
            }
        }
        //异步方法不会阻塞并立即返回。一旦它完成了，ActionListener就会被调用回onResponse方法(如果执行成功的话)，
        // 或者使用onFailure方法(如果执行失败的话)。失败场景和预期的异常与同步执行情况相同。
//        highLevelClient.indexAsync(request, RequestOptions.DEFAULT, listener);
    }

    public void source(String index, String id, String jsonString){
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.source(jsonString, XContentType.JSON);
        try {
            highLevelClient.index(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ElasticsearchException e){
            if (e.status() == RestStatus.CONFLICT) {}
        }
    }

    //map
    public void source(String index, String id, Map<String, Object> jsonMap){
//        jsonMap = new HashMap<>();
//        jsonMap.put("user", "wjc");
//        jsonMap.put("postDate", new Date());
//        jsonMap.put("message", "trying out Elasticsearch2");

//        IndexRequest request = new IndexRequest("posts").id("2").source(jsonMap);
        IndexRequest request = new IndexRequest(index).id(id).source(jsonMap);
        try {
            IndexResponse indexResponse = highLevelClient.index(request, RequestOptions.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ElasticsearchException e){
            if (e.status() == RestStatus.CONFLICT) {
                //引发的异常表明返回了版本冲突错误
            }
        }
    }

    public void sourceForBuilder() throws IOException{
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "wjc");
            builder.timeField("postDate", new Date());
            builder.field("message", "trying out Elasticsearch3");
        }
        builder.endObject();

        IndexRequest request = new IndexRequest("posts").id("3").source(builder);
        IndexResponse indexResponse = highLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public void source() throws IOException{
        IndexRequest request = new IndexRequest("posts")
                .id("1")
                .source("user", "kimchy",
                        "postDate", new Date(),
                        "message", "trying out Elasticsearch");

        IndexResponse indexResponse = highLevelClient.index(request, RequestOptions.DEFAULT);
    }
}
