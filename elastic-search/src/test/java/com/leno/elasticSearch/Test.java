package com.leno.elasticSearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) throws Exception{
//        index();
//        match();
//        get();
        search();

        close();
    }

    public static void close() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        restHighLevelClient.close();
    }

    public static void index(){
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        IndexRequest indexRequest = new IndexRequest("seven", "five");
        News news = new News();
        news.setTitle("da feiji");
        news.setTag("feiji");
        news.setPublishTime("2018-01-24T23:59:30Z");
        String source = JSON.toJSONString(news);
        indexRequest.source(source, XContentType.JSON);
        try {
            restHighLevelClient.index(indexRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void get() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        GetRequest getRequest = new GetRequest("seven", "five", "AWJDeSjhqbV5aANTF0eI");
        GetResponse re = restHighLevelClient.get(getRequest);
        System.out.println(re.getSourceAsString());
    }

    public static void search() throws Exception {
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.termQuery("title", "feiji"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest request = new SearchRequest("seven");
        request.types("five");
        request.source(sourceBuilder);

        SearchResponse rep = restHighLevelClient.search(request);
        System.out.println(rep.toString());
    }

    public static void match(){
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", "feiji");
        Object obj = matchQueryBuilder.DEFAULT_OPERATOR;
        System.out.println(obj);
    }


}
