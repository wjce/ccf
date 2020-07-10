package com.wjc.ccf.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Configuration
public class ESBulkListener {
    @Bean("bulkListener")
    public ActionListener listener(){
        ActionListener<BulkResponse> listener = new ActionListener<BulkResponse>() {
            @Override
            public void onResponse(BulkResponse bulkResponse) {
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    DocWriteResponse itemResponse = bulkItemResponse.getResponse();

                    switch (bulkItemResponse.getOpType()) {
                        case INDEX:
                        case CREATE:
                            IndexResponse indexResponse = (IndexResponse) itemResponse;
                            break;
                        case UPDATE:
                            UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                            break;
                        case DELETE:
                            DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    }
                }

                //如果至少有一个操作失败，此方法将返回true
                if (bulkResponse.hasFailures()) {

                }

                //判断所有的操作结果
                for (BulkItemResponse bulkItemResponse : bulkResponse) {
                    //如果操作失败，进行处理
                    if (bulkItemResponse.isFailed()) {
                        BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        };
        return listener;
    }

}
