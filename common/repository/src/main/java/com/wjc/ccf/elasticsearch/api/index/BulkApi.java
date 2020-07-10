package com.wjc.ccf.elasticsearch.api.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Component
@Slf4j
public class BulkApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("bulkListener")
    private ActionListener listener;



    public void index(){
        //在所有子请求上使用全局索引和类型的大容量请求，除非在子请求上重写。这两个参数都是@Nullable，只能在创建BulkRequest时设置
        //BulkRequest defaulted = new BulkRequest("posts","_doc");
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts").id("1")
                .source(XContentType.JSON,"field", "foo"));
        request.add(new IndexRequest("posts").id("2")
                .source(XContentType.JSON,"field", "bar"));
        request.add(new IndexRequest("posts").id("3")
                .source(XContentType.JSON,"field", "baz"));
        request.add(new DeleteRequest("posts", "3"));
        request.add(new UpdateRequest("posts", "2")
                .doc(XContentType.JSON,"other", "test"));
        request.add(new IndexRequest("posts").id("4")
                .source(XContentType.JSON,"field", "baz"));

//        request.timeout(TimeValue.timeValueMinutes(2));
//        request.timeout("2m");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
//        request.setRefreshPolicy("wait_for");
//        request.waitForActiveShards(2);
//        request.waitForActiveShards(ActiveShardCount.ALL);
//        request.pipeline("pipelineId");
//        request.routing("routingId");

        try {
            BulkResponse bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        }catch (IOException e){

        }
        client.bulkAsync(request, RequestOptions.DEFAULT, listener);
    }

    //BulkProcessor通过提供一个实用程序类来简化批量API的使用，
    // 这个实用程序类允许在索引/更新/删除操作被添加到processo时透明地执行这些操作
    public void bulkProcessor(){
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            //此方法在每次执行BulkRequest之前调用
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                //在执行BulkRequest之前调用，这个方法允许知道将要在BulkRequest中执行的操作的数量
                int numberOfActions = request.numberOfActions();
                log.debug("Executing bulk [{}] with {} requests",
                        executionId, numberOfActions);
            }

            //此方法在每次执行BulkRequest之后调用
            @Override
            public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                //在每次执行BulkRequest之后调用，这个方法允许知道BulkResponse是否包含错误
                if (response.hasFailures()) {
                    log.warn("Bulk [{}] executed with failures", executionId);
                } else {
                    log.debug("Bulk [{}] completed in {} milliseconds",
                            executionId, response.getTook().getMillis());
                }
            }

            //当BulkRequest失败时调用此方法
            @Override
            public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                //如果BulkRequest失败，则调用该方法，该方法允许知道失败
                log.error("Failed to execute bulk", failure);
            }
        };

        //BulkProcessor.Builder提供了配置BulkProcessor如何处理请求执行的方法
        BiConsumer<BulkRequest, ActionListener<BulkResponse>> bulkConsumer =
                (request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener);
        //通过从BulkProcessor. builder中调用build()方法来创建BulkProcessor。将使用reslevelclient . bulkasync()方法在后台执行BulkRequest
        BulkProcessor bulkProcessor = BulkProcessor.builder(bulkConsumer, listener).build();
        BulkProcessor.Builder builder = bulkProcessor.builder(bulkConsumer, listener);
        //根据当前添加的操作数设置刷新新批量请求的时间(默认为1000，使用-1禁用它)
        builder.setBulkActions(500);
        //根据当前添加操作的大小设置刷新新批量请求的时间(默认为5Mb，使用-1禁用它)
        builder.setBulkSize(new ByteSizeValue(1L, ByteSizeUnit.MB));
        //设置允许执行的并发请求数量(默认为1，使用0只允许执行单个请求)
        builder.setConcurrentRequests(0);
        //如果间隔通过，则设置刷新间隔刷新任何BulkRequest(默认为未设置)
        builder.setFlushInterval(TimeValue.timeValueSeconds(10L));
        //设置一个常数回退策略，该策略最初等待1秒并重试最多3次。有关更多选项，请参见BackoffPolicy.noBackoff()、
        // BackoffPolicy.constantBackoff()和BackoffPolicy.exponentialBackoff()。
        builder.setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3));

        IndexRequest one = new IndexRequest("posts").id("1")
                .source(XContentType.JSON, "title",
                        "In which order are my Elasticsearch queries executed?");
        IndexRequest two = new IndexRequest("posts").id("2")
                .source(XContentType.JSON, "title",
                        "Current status and upcoming changes in Elasticsearch");
        IndexRequest three = new IndexRequest("posts").id("3")
                .source(XContentType.JSON, "title",
                        "The Future of Federated Search in Elasticsearch");

        bulkProcessor.add(one);
        bulkProcessor.add(two);
        bulkProcessor.add(three);

        //将所有请求添加到BulkProcessor之后，需要使用两种可用的关闭方法中的一种关闭它的实例
        //可以使用awaitClose()方法等待，直到处理完所有请求或指定的等待时间过期
        //如果所有大容量请求都已完成，该方法将返回true;如果在所有大容量请求完成之前等待时间已经过去，则返回false
        try {
            boolean terminated = bulkProcessor.awaitClose(30L, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //可以使用close()方法立即关闭BulkProcessor
        //这两个方法都在关闭处理器之前刷新添加到处理器的请求，并且还禁止向处理器添加任何新请求
        bulkProcessor.close();
    }
}
