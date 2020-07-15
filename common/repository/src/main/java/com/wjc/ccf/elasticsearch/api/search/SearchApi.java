package com.wjc.ccf.elasticsearch.api.search;

import com.google.gson.Gson;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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

    public String termQuery(String indices, String name, String value){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery(name, value));
        //分页
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        //超时时间设置
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        return getHits(searchRequest);
    }

    public String matchQuery(String indices, String fieldName, String value){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //模糊搜索  设置前缀长度  设置最大扩展选项来控制查询的模糊过程
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(fieldName, value)
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(0)
                .maxExpansions(10);

        sourceBuilder.query(matchQueryBuilder);
        searchRequest.source(sourceBuilder);
        return getHits(searchRequest);
    }

    public String highLightQuery(String indices, String name, String text, String... field){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery(name, text).prefixLength(0));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        for (String s : field) {
            highlightBuilder.field(s);
        }
        highlightBuilder.preTags("<span style=\"color:yellow\">");
        highlightBuilder.postTags("</span>");
        highlightBuilder.highlighterType("unified");
//        highlightBuilder.requireFieldMatch(true);
//        highlightBuilder.numOfFragments(0);
        searchSourceBuilder.highlighter(highlightBuilder);

        searchSourceBuilder.from(0);
        searchSourceBuilder.size(40);
        searchRequest.source(searchSourceBuilder);
        return getHits(searchRequest, field);
    }

    public String aggregationsQuery(String indices, String aggKey, String avgKey, String termField, String field){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        /**
         * 汇总  通过首先创建适当的AggregationBuilder，然后在SearchSourceBuilder上设置它，可以将聚合添加到搜索中
         * Building Aggregations页面提供了所有可用聚合的列表，以及它们对应的AggregationBuilder对象和AggregationBuilders helper方法
         * AggregationBuilders.terms 相当于sql中的group by;  terms值自定义 termField为需要分组的key
         * .subAggregation()相当于count
         * 获取不同性别的总人数 select gender, count(*) as termField from bank group by gender   #(gender = field)
         */
        TermsAggregationBuilder aggregation = AggregationBuilders.terms(aggKey).field(termField);
//        aggregation.subAggregation(AggregationBuilders.avg(avgKey).field(field));
        sourceBuilder.aggregation(aggregation);
        searchRequest.source(sourceBuilder);
        return getAggregations(searchRequest, aggKey, avgKey);
    }

    public String suggestionQuery(String indices, String fieldname, String text, String name){
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion(fieldname).text(text);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion(name, termSuggestionBuilder);
        sourceBuilder.suggest(suggestBuilder);
        searchRequest.source(sourceBuilder);
        return suggestionSendSearch(searchRequest, name);
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

        //汇总  通过首先创建适当的AggregationBuilder，然后在SearchSourceBuilder上设置它，可以将聚合添加到搜索中
        //Building Aggregations页面提供了所有可用聚合的列表，以及它们对应的AggregationBuilder对象和AggregationBuilders helper方法
        //创建公司名称上的术语聚合，以及公司员工平均年龄上的子聚合
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_company").field("company.keyword");
        aggregation.subAggregation(AggregationBuilders.avg("average_age").field("age"));
        sourceBuilder.aggregation(aggregation);

        //向搜索请求添加建议
        SuggestionBuilder termSuggestionBuilder = SuggestBuilders.termSuggestion("user").text("kmichy");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_user", termSuggestionBuilder);
        sourceBuilder.suggest(suggestBuilder);

        //Profile API可用于概要分析特定搜索请求的查询和聚合的执行情况。为了使用它，必须在SearchSourceBuilder中将概要文件标志设置为true
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        searchSourceBuilder.profile(true);

        //sourceBuilder添加到searchRequest中
        searchRequest.source(sourceBuilder);

        /**  同步查询  */
        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            Aggregations aggregations = searchResponse.getAggregations();
            Terms byCompanyAggregation = aggregations.get("by_company");
            MultiBucketsAggregation.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
            Avg averageAge = elasticBucket.getAggregations().get("average_age");
            double avg = averageAge.getValue();
        }catch (Exception e){
            e.printStackTrace();
        }

        /**  异步查询  */
        client.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);
        return result;
    }

    public String getHits(SearchRequest searchRequest, String... highLightField){
        SearchResponse searchResponse = syncSendSearch(searchRequest);
        StringBuilder result = new StringBuilder();
        SearchHits hits = searchResponse.getHits();
//        TotalHits totalHits = hits.getTotalHits();
        //命中的总次数，必须在totalHits.relation上下文中解释
//        long numHits = totalHits.value;
        //命中的次数是准确的(EQUAL_TO)还是总数的下限(GREATER_THAN_OR_EQUAL_TO)
//        TotalHits.Relation relation = totalHits.relation;
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            //它允许您返回文档源，可以是简单的json字符串，也可以是键/值对的映射。
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            List<Object> users = (List<Object>) sourceAsMap.get(field);
//            Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get(field);
//如果请求，可以从结果中的每个SearchHit中检索突出显示的文本片段。
            // hit对象提供了对字段名到HighlightField实例的映射的访问，每个实例都包含一个或多个高亮显示的文本片段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if(highlightFields.size() > 0 && highLightField != null){
                for (String s : highLightField) {
                    HighlightField highlight = highlightFields.get(s);
                    Text[] fragments = highlight.fragments();
                    String fragmentString = fragments[0].string();
                    //替换为高亮
                    sourceAsMap.put(s, fragmentString);
                }
            }
            // 在此映射中，常规字段按字段名进行键控，并包含字段值。多值字段作为对象列表返回，嵌套对象作为另一个键/值映射返回
            String sourceAsString = hit.getSourceAsString();
            Gson gson = new Gson();
            result.append(gson.toJson(sourceAsMap));
        }

        return result.toString();
    }

    //可以通过首先获取聚合树的根、聚合对象，然后通过名称获取聚合，从SearchResponse中检索聚合
    public String getAggregations(SearchRequest searchRequest, String aggKey, String avgKey){
        SearchResponse searchResponse = syncSendSearch(searchRequest);
        Aggregations aggregations = searchResponse.getAggregations();
        Map map = aggregations.getAsMap();
        Terms byCompanyAggregation = aggregations.get(aggKey);
        List list = byCompanyAggregation.getBuckets();
        MultiBucketsAggregation.Bucket elasticBucket = byCompanyAggregation.getBucketByKey(aggKey);
        Avg averageAge = elasticBucket.getAggregations().get(avgKey);
        double avg = averageAge.getValue();
        //注意，如果按名称访问聚合，则需要根据请求的聚合类型指定聚合接口，否则将抛出ClassCastException
        //这将抛出一个异常，因为“by_company”是一个术语聚合，但我们试图以范围聚合的形式检索它
//        Range range = aggregations.get(aggKey);

        //还可以将所有聚合作为映射访问，映射是由聚合名称键入的。在这种情况下，需要显式地转换到适当的聚合接口
//        Map<String, Aggregation> aggregationMap = aggregations.getAsMap();
//        Terms companyAggregation = (Terms) aggregationMap.get(aggKey);

        //还有一些getter方法以列表的形式返回所有顶级聚合
//        List<Aggregation> aggregationList = aggregations.asList();
//        for (Aggregation agg : aggregations) {
//            String type = agg.getType();
//            if (type.equals(TermsAggregationBuilder.NAME)) {
//                Terms.Bucket elasticBucket2 = ((Terms) agg).getBucketByKey("Elastic");
//                long numberOfDocs = elasticBucket2.getDocCount();
//            }
//        }
        return avg + "";
    }

    public String suggestionSendSearch(SearchRequest searchRequest, String name){
        SearchResponse searchResponse = syncSendSearch(searchRequest);
        StringBuilder result = new StringBuilder();
        Suggest suggest = searchResponse.getSuggest();
        TermSuggestion termSuggestion = suggest.getSuggestion(name);
        for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
            for (TermSuggestion.Entry.Option option : entry) {
                String suggestText = option.getText().string();
                result.append(suggestText);
            }
        }

        return result.toString();
    }

    public SearchResponse syncSendSearch(SearchRequest searchRequest){
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            RestStatus status = searchResponse.status();
            TimeValue took = searchResponse.getTook();
            Boolean terminatedEarly = searchResponse.isTerminatedEarly();
            boolean timedOut = searchResponse.isTimedOut();

        }catch (Exception e){
            e.printStackTrace();
        }

        return searchResponse;
    }
    public void asyncSendSearch(SearchRequest searchRequest){
        client.searchAsync(searchRequest, RequestOptions.DEFAULT, listener);
    }
}
