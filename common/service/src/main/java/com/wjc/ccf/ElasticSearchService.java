package com.wjc.ccf;

import com.wjc.ccf.elasticsearch.api.index.*;
import com.wjc.ccf.elasticsearch.api.search.SearchApi;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/9
 */
@Service
public class ElasticSearchService {

    @Autowired
    private IndexApi indexApi;
    @Autowired
    private UpdateApi updateApi;
    @Autowired
    private DeleteApi deleteApi;
    @Autowired
    private BulkApi bulkApi;
    @Autowired
    private GetApi getApi;
    @Autowired
    private MultiGetApi multiGetApi;
    @Autowired
    private SearchApi searchApi;

    @SuppressWarnings("unchecked")
    public void source(String index, String id, Object object){
        if(object == null){
            return;
        }else{
            if(object instanceof String){
                indexApi.source(index, id, object.toString());
            }else if (object instanceof Map){
                indexApi.source(index, id, (HashMap) object);
            }
        }
    }
    public void source(){

    }

    public void delete(String index, String id){
        deleteApi.delete(index, id);
    }

    public void update(String index, String id, String jsonString){
        updateApi.updateJSON(index, id, jsonString);
    }

    public String get(String index, String id){
        if(StringUtils.isBlank(index) || StringUtils.isBlank(id)){
            return "";
        }
        return getApi.get(index, id);
    }

    public String search(){
        return searchApi.search();
    }

    public String termSearch(String indices, String name, Object value){
        return searchApi.termQuery(indices, name, value);
    }

    public String matchSearch(String indices, String fieldName, Object value){
        return searchApi.matchQuery(indices, fieldName, value);
    }

    public String highLightSearch(String indices, String name, Object text, String... field){
        return searchApi.highLightQuery(indices, name, text, field);
    }

    public String aggregationsSearch(String indices, String aggKey, String avgKey, String termField, String field){
        return searchApi.aggregationsQuery(indices, aggKey, avgKey, termField, field);
    }

    public String suggestionSearch(String indices, String fieldname, String text, String name){
        return searchApi.suggestionQuery(indices, fieldname, text, name);
    }
}
