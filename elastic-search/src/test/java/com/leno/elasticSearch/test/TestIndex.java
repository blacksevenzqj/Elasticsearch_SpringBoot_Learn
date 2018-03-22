package com.leno.elasticSearch.test;

import com.leno.elasticSearch.ESClientFactory;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;

public class TestIndex {

    public static void main() throws Exception{
        createIndex();

        close();
    }

    public static void createIndex() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();

        IndexRequest indexRequest = new IndexRequest("xiamo", "school", "1")
                .source("articleID", "1111");

        restHighLevelClient.index(indexRequest);
    }

    public static void close() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        restHighLevelClient.close();
    }


}
