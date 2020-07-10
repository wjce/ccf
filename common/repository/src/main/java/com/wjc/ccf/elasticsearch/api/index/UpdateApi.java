package com.wjc.ccf.elasticsearch.api.index;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Component
public class UpdateApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("updateListener")
    private ActionListener listener;

    //以JSON格式提供的部分文档源
    public void updateJSON(String index, String id, String jsonStrings){
        UpdateRequest request = new UpdateRequest("posts", "1");
//        String jsonString = "{" +
//                "\"updated\":\"2017-01-01\"," +
//                "\"reason\":\"daily update\"" +
//                "}";
        String jsonString = "{" +
                "\"user\":\"sdfdasfdsa\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"fdsfdasfdsfsd1\"" +
                "}";

        request.doc(jsonString, XContentType.JSON);
        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        }catch (IOException e){

        }catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                //处理因文档不存在而引发的异常
            }
        }
        client.updateAsync(request, RequestOptions.DEFAULT, listener);
    }

    //以映射形式提供的部分文档源，自动转换为JSON格式
    public void updateMap(){
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        UpdateRequest request = new UpdateRequest("posts", "doc", "1").doc(jsonMap);

        //如果文档不存在，可以使用upsert方法定义一些内容，这些内容将作为新文档插入
        String jsonString = "{\"created\":\"2017-01-01\"}";
        request.upsert(jsonString, XContentType.JSON);
        request.routing("routing");
        request.timeout(TimeValue.timeValueSeconds(1));
//        request.timeout("1s");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
        //如果要更新的文档在更新操作的get和索引阶段之间被另一个操作更改，那么要重试多少次更新操作
        request.retryOnConflict(3);
        request.fetchSource(true);
        request.version(2);
        String[] includes = new String[]{"updated", "r*"};
//        String[] includes = Strings.EMPTY_ARRAY;
//        String[] excludes = new String[]{"updated"};
        String[] excludes = Strings.EMPTY_ARRAY;
        request.fetchSource(new FetchSourceContext(true, includes, excludes));
        //禁用noop检测
        request.detectNoop(false);
        //表明无论文档是否存在，脚本都必须运行，即如果文档不存在，脚本将负责创建文档
        request.scriptedUpsert(false);
        //设置在继续执行更新操作之前必须处于活动状态的碎片副本的数量
        request.waitForActiveShards(2);
        //作为ActiveShardCount提供的碎片副本数量:可以是ActiveShardCount。ActiveShardCount。一个或ActiveShardCount。默认(默认)
        request.waitForActiveShards(ActiveShardCount.ALL);

        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        }catch (IOException e){

        }catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                //处理因文档不存在而引发的异常
            }
        }
        client.updateAsync(request, RequestOptions.DEFAULT, listener);
    }

    //部分文档源作为一个XContentBuilder对象提供，Elasticsearch内置帮助生成JSON内容
    public void updateBuilder(){
        try {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.timeField("updated", new Date());
                builder.field("reason", "daily update");
            }
            builder.endObject();

            UpdateRequest request = new UpdateRequest("posts", "1").doc(builder);
        }catch (IOException e){

        }
    }

    //作为对象键对提供的部分文档源，它被转换为JSON格式
    public void update(){
        UpdateRequest request = new UpdateRequest("posts", "1")
                .doc("updated", new Date(), "reason", "daily update");
    }

    public void updateWithScript(String index, String type, String id){
        UpdateRequest request = new UpdateRequest("posts", "1");

        //作为Map对象提供的脚本参数
        Map<String, Object> parameters = singletonMap("count", 4);
        //使用painless语言和先前的参数创建内联脚本
        Script inline = new Script(ScriptType.INLINE, "painless", "ctx._source.field += params.count", parameters);
        //将脚本设置为更新请求
        request.script(inline);
    }
}
