package com.wjc.ccf.elasticsearch.api.search;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author wjc
 * @description
 * @date 2020/5/13
 */
@Component
public class SearchApi {

    @Autowired
    private RestHighLevelClient client;
    @Autowired
    @Qualifier("searchListener")
    private ActionListener listener;

    public String search(String index){
        SearchRequest searchRequest = new SearchRequest("bank");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//        sourceBuilder.query(QueryBuilders.termQuery("age", "32"));
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("gender", "M");
//        MatchQueryBuilder matchQueryBuilder2 = new MatchQueryBuilder("firstname", "Amber");
//        MatchQueryBuilder matchQueryBuilder3 = new MatchQueryBuilder("lastname", "Mann");
        MatchQueryBuilder likeQuery = new MatchQueryBuilder("lastname", "Man")
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(0)
//                .maxExpansions(10)
                ;

        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age").gt(25).lt(45);
//        sourceBuilder.query(likeQuery);
        sourceBuilder.query(rangeQueryBuilder);
//        sourceBuilder.query(QueryBuilders.termQuery("gender", "M"));
//        sourceBuilder.from(0);
//        sourceBuilder.size(5);
        sourceBuilder.sort(new FieldSortBuilder("age").order(SortOrder.DESC));
        sourceBuilder.sort(new FieldSortBuilder("account_number").order(SortOrder.DESC));
        StringBuffer stringBuffer = new StringBuffer();
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            hits.forEach(hit -> stringBuffer.append(hit.getSourceAsString()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }


    public String search(){
        String result = null;
        //创建SearchRequest。如果没有参数，这将对所有索引运行
        SearchRequest searchRequest = new SearchRequest("index");
        //可选参数
        searchRequest.routing("routing");
        //设置IndicesOptions可以控制如何解析不可用的索引以及如何展开通配符表达式
        searchRequest.indicesOptions(IndicesOptions.lenientExpandOpen());
        searchRequest.preference("_local");

        /**  高亮  */
        //大多数搜索参数被添加到SearchSourceBuilder。它为进入搜索请求体的所有内容提供setter
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //向SearchSourceBuilder添加一个match_all查询
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        //突出显示搜索结果可以通过设置来实现HighlightBuilder的  SearchSourceBuilder。
        // 通过向中添加一个或多个HighlightBuilder.Field实例，可以为每个字段定义不同的突出显示行为HighlightBuilder
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //为该title字段创建一个字段荧光笔
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        //设置字段荧光笔类型
        highlightTitle.highlighterType("unified");
        //将字段突出显示器添加到突出显示生成器
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightUser = new HighlightBuilder.Field("user");
        highlightBuilder.field(highlightUser);
        sourceBuilder.highlighter(highlightBuilder);

        //使用SearchSourceBuilderedit
        /** 大多数控制搜索行为的选项都可以在SearchSourceBuilder上设置，它或多或少地包含了Rest API的搜索请求体中的选项 */
        //设置查询。可以是任何类型的QueryBuilder吗
        sourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        /**     分页   排序   */
        //设置from选项，确定要开始搜索的结果索引。默认值为0。
        sourceBuilder.from(0);
        //设置确定要返回的搜索结果数量的大小选项。默认为10。
        sourceBuilder.size(5);
        //设置一个可选的超时，控制允许搜索的时间
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //按_score降序排序(默认值)
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //按字段升序排序
        sourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));
        //完全关闭检索
        sourceBuilder.fetchSource(false);
        //一个或多个通配符模式的数组，以控制以更细粒度的方式包含或排除哪些字段
        String[] includeFields = new String[] {"title", "innerObject.*"};
        String[] excludeFields = new String[] {"user"};
        sourceBuilder.fetchSource(includeFields, excludeFields);
//        SearchRequest searchRequest = new SearchRequest();
//        searchRequest.indices("posts");

        /** 搜索查询是使用QueryBuilder对象创建的。对于Elasticsearch的查询DSL支持的每个搜索查询类型，都存在一个QueryBuilder。
         可以使用QueryBuilder的构造函数创建QueryBuilder */
        //创建一个全文本匹配查询，在“user”字段上匹配文本“kimchy”
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("user", "kimchy");
        /** 创建后，QueryBuilder对象提供了一些方法来配置它创建的搜索查询的选项 */
        //在匹配查询上启用模糊匹配
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        //在匹配查询中设置前缀长度选项
//        matchQueryBuilder.prefixLength(3);
        //设置最大扩展选项，控制查询的模糊过程
//        matchQueryBuilder.maxExpansions(10);
        /** 还可以使用QueryBuilders实用程序类创建QueryBuilder对象。该类提供了一些辅助方法，
         * 可以使用这些方法使用流畅的编程风格创建QueryBuilder对象 */
//        QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
//                .fuzziness(Fuzziness.AUTO)      //对匹配查询启用模糊匹配
//                .prefixLength(3)                //在匹配查询中设置前缀长度选项
//                .maxExpansions(10);             //设置最大扩展选项以控制查询的模糊过程
//        sourceBuilder.query(matchQueryBuilder);

        //sourceBuilder添加到searchRequest中
        searchRequest.source(sourceBuilder);

        /**  同步查询  */
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            e.printStackTrace();
        }

        /**  异步查询  */
        client.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);
        return result;
    }
}
