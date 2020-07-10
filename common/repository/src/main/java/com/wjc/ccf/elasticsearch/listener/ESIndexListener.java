package com.wjc.ccf.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Configuration
public class ESIndexListener {
    @Bean("indexListener")
    public ActionListener listener(){
        ActionListener listener = new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println(indexResponse.status().getStatus());
                System.out.println(indexResponse.getIndex());

                String index = indexResponse.getIndex();
                String id = indexResponse.getId();
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    //(如果需要处理)文档第一次创建的情况
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    //(如果需要处理)文档已经被重写的情况
                }
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    //处理成功分片的数量少于总分片的情况
                }
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure :
                            shardInfo.getFailures()) {
                        //处理潜在的故障
                        String reason = failure.reason();
                    }
                }
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        };
        return listener;
    }
}
