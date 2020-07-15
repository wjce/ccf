package com.wjc.ccf.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wjc.ccf.ElasticSearchService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/13
 */
@RestController
public class ElasticSearchController {
    @Autowired
    private ElasticSearchService elasticSearchService;

    @PostMapping("/es/index/source")
    public String source(@RequestBody JSONObject jsonObject){
        String index = jsonObject.getString("index");
        String id = jsonObject.getString("id");
        JSONObject source = jsonObject.getJSONObject("source");
        Map<String, String> map = JSON.parseObject(source.toJSONString(), HashMap.class);
        elasticSearchService.source(index, id, map);
        return "success";
    }

    @PostMapping("/es/delete")
    public String delete(String index ,String id){

        elasticSearchService.delete(index, id);
        return "success";
    }

    @PostMapping("/es/update")
    public String update(String index ,String id, String jsonString){
        elasticSearchService.update(index, id, jsonString);
        return "success";
    }


    @PostMapping("/es/get/get")
    public String get(String index, String id){
        return elasticSearchService.get(index, id);
    }

    @PostMapping("/es/search")
    public String search(){return elasticSearchService.search();}

    @PostMapping("/es/termSearch")
    public String termSearch(String indices, String name, Object value){return elasticSearchService.termSearch(indices, name, value);}

    @PostMapping("/es/matchSearch")
    public String matchSearch(String indices, String fieldName, Object value){return elasticSearchService.matchSearch(indices, fieldName, value);}

    @PostMapping("/es/highLightSearch")
    public String highLightSearch(String indices, String name, Object text, String... field){return elasticSearchService.highLightSearch(indices, name, text, field);}

    @PostMapping("/es/aggregationsSearch")
    public String aggregationsSearch(String indices, String aggKey, String avgKey, String termField, String field){return elasticSearchService.aggregationsSearch(indices, aggKey, avgKey, termField, field);}

    @PostMapping("/es/suggestionSearch")
    public String suggestionSearch(String indices, String fieldname, String text, String name){return elasticSearchService.suggestionSearch(indices, fieldname, text, name);}
}
