package com.wjc.ccf.elasticsearch.listener;

import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.MultiBucketsAggregation;
import org.elasticsearch.search.aggregations.bucket.range.Range;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * @author wjc
 * @description
 * @date 2020/5/13
 */
@Configuration
public class ESSearchListener {

    @Bean("searchListener")
    public ActionListener listener(){
        ActionListener<SearchResponse> listener = new ActionListener<SearchResponse>() {
            //成功时调用
            @Override
            public void onResponse(SearchResponse searchResponse) {
                //HTTP状态码、执行时间或请求是否提前终止或超时
                RestStatus status = searchResponse.status();
                TimeValue took = searchResponse.getTook();
                Boolean terminatedEarly = searchResponse.isTerminatedEarly();
                boolean timedOut = searchResponse.isTimedOut();
                //受搜索影响的切分总数以及成功与失败切分的统计信息，从而提供关于切分级别上执行的信息。
                int totalShards = searchResponse.getTotalShards();
                int successfulShards = searchResponse.getSuccessfulShards();
                int failedShards = searchResponse.getFailedShards();
                // 还可以通过在shardsearchfailure上迭代数组来处理可能的失败
                for (ShardSearchFailure failure : searchResponse.getShardFailures()) {
                    // failures should be handled here
                }

                //要访问返回的文档，首先需要获得响应中包含的SearchHits
                SearchHits hits = searchResponse.getHits();
                TotalHits totalHits = hits.getTotalHits();
                //命中的总次数，必须在totalHits.relation上下文中解释
                long numHits = totalHits.value;
                //命中的次数是准确的(EQUAL_TO)还是总数的下限(GREATER_THAN_OR_EQUAL_TO)
                TotalHits.Relation relation = totalHits.relation;
                float maxScore = hits.getMaxScore();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit hit : searchHits) {
                    String index = hit.getIndex();
                    String id = hit.getId();
                    float score = hit.getScore();
                    //它允许您返回文档源，可以是简单的json字符串，也可以是键/值对的映射。
                    // 在此映射中，常规字段按字段名进行键控，并包含字段值。多值字段作为对象列表返回，嵌套对象作为另一个键/值映射返回
                    String sourceAsString = hit.getSourceAsString();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    String documentTitle = (String) sourceAsMap.get("title");
                    List<Object> users = (List<Object>) sourceAsMap.get("user");
                    Map<String, Object> innerObject = (Map<String, Object>) sourceAsMap.get("innerObject");
//如果请求，可以从结果中的每个SearchHit中检索突出显示的文本片段。
                    // hit对象提供了对字段名到HighlightField实例的映射的访问，每个实例都包含一个或多个高亮显示的文本片段
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField highlight = highlightFields.get("title");
                    Text[] fragments = highlight.fragments();
                    String fragmentString = fragments[0].string();

                }

                //可以通过首先获取聚合树的根、聚合对象，然后通过名称获取聚合，从SearchResponse中检索聚合
                Aggregations aggregations = searchResponse.getAggregations();
                //获取by_company汇总
                Terms byCompanyAggregation = aggregations.get("by_company");
                //获取带有密钥的存储桶
                Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
                //从该存储桶中获取子聚合
                Avg averageAge = elasticBucket.getAggregations().get("average_age");
                double avg = averageAge.getValue();
                //注意，如果按名称访问聚合，则需要根据请求的聚合类型指定聚合接口，否则将抛出ClassCastException
                //这将抛出一个异常，因为“by_company”是一个术语聚合，但我们试图以范围聚合的形式检索它
                Range range = aggregations.get("by_company");

                //还可以将所有聚合作为映射访问，映射是由聚合名称键入的。在这种情况下，需要显式地转换到适当的聚合接口
                Map<String, Aggregation> aggregationMap = aggregations.getAsMap();
                Terms companyAggregation = (Terms) aggregationMap.get("by_company");

                //还有一些getter方法以列表的形式返回所有顶级聚合
                List<Aggregation> aggregationList = aggregations.asList();
                for (Aggregation agg : aggregations) {
                    String type = agg.getType();
                    if (type.equals(TermsAggregationBuilder.NAME)) {
                        Terms.Bucket elasticBucket2 = ((Terms) agg).getBucketByKey("Elastic");
                        long numberOfDocs = elasticBucket2.getDocCount();
                    }
                }


                //使用Suggest对象作为入口点，然后检索嵌套的Suggest对象
                Suggest suggest = searchResponse.getSuggest();
                TermSuggestion termSuggestion = suggest.getSuggestion("suggest_user");
                for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
                    for (TermSuggestion.Entry.Option option : entry) {
                        String suggestText = option.getText().string();
                    }
                }

                //使用getProfileResults()方法从SearchResponse中检索分析结果。该方法返回一个映射，其中包含一个ProfileShardResult对象，
                // 用于SearchRequest执行中涉及的每个碎片。
                // ProfileShardResult使用唯一标识概要结果对应的分片的键存储在映射中
                Map<String, ProfileShardResult> profilingResults = searchResponse.getProfileResults();
                for (Map.Entry<String, ProfileShardResult> profilingResult : profilingResults.entrySet()) {
                    String key = profilingResult.getKey();
                    ProfileShardResult profileShardResult = profilingResult.getValue();
                }
            }

            //失败时调用
            @Override
            public void onFailure(Exception e) {

            }
        };

        return listener;
    }

}
