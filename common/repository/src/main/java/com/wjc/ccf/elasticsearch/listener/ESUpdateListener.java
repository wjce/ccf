package com.wjc.ccf.elasticsearch.listener;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.get.GetResult;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Configuration
public class ESUpdateListener {
    @Bean("updateListener")
    public ActionListener listener(){
        ActionListener listener = new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                String index = updateResponse.getIndex();
                String type = updateResponse.getType();
                String id = updateResponse.getId();
                long version = updateResponse.getVersion();

                if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    //处理第一次创建文档的情况(upsert)
                } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    //处理文档更新的情况
                } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
                    //处理删除文档的情况
                } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
                    //处理文档不受更新影响的情况，即对文档不执行任何操作(noop)
                }

                //当通过fetchSource方法在UpdateRequest中启用源检索时，响应包含已更新文档的源
                //以GetResult的形式检索更新后的文档
                GetResult result = updateResponse.getGetResult();
                if (result.isExists()) {
                    //以字符串的形式检索已更新文档的源
                    String sourceAsString = result.sourceAsString();
                    //以Map<String, Object>的形式检索更新后的文档的源
                    Map<String, Object> sourceAsMap = result.sourceAsMap();
                    //以字节[]的形式检索已更新文档的源
                    byte[] sourceAsBytes = result.source();
                } else {
                    //处理响应中不存在文档源的场景(默认情况下是这种情况)
                }

                //还可以检查碎片故障
                ReplicationResponse.ShardInfo shardInfo = updateResponse.getShardInfo();
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

            }
        };
        return listener;
    }
}
