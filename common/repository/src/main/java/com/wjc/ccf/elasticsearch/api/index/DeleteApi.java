package com.wjc.ccf.elasticsearch.api.index;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author wjc
 * @description
 * @date 2020/5/10
 */
@Slf4j
@Component
public class DeleteApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("deleteListener")
    private ActionListener listener;


    public void delete(String index, String id){
        DeleteRequest request = new DeleteRequest(index, id);
//        request.routing("routing");
//        request.timeout(TimeValue.timeValueMinutes(2));
////        request.timeout("2m");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
////        request.setRefreshPolicy("wait_for");
//        request.version(2);
//        request.versionType(VersionType.EXTERNAL);

        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                //如果要删除的文档没有找到，执行什么操作
                log.info("delete index:" + index + ",id: " + id + "faild");
                return;
            }
            client.deleteAsync(request, RequestOptions.DEFAULT, listener);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.CONFLICT) {
                //引发的异常表明返回了版本冲突错误
            }
        }
    }
}
