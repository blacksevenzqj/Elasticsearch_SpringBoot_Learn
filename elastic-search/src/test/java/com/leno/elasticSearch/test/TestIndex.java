package com.leno.elasticSearch.test;

import com.leno.elasticSearch.ESClientFactory;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;

public class TestIndex {

    public static void main(String[] args) throws Exception{
//        createIndex();
        getIndex();


        close();
    }

    public static void createIndex() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        IndexRequest indexRequest = new IndexRequest("xiamo", "school", "1")
                .source("articleID", "qwe1123ghf",
                        "userID", "19",
                        "hidden", "false",
                        "postDate", "2018-03-21"
                );
        restHighLevelClient.index(indexRequest);
    }

    public static void getIndex() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        GetRequest getRequest = new GetRequest("xiamo", "school", "1");
        System.out.println(restHighLevelClient.exists(getRequest));
    }

    public static void close() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        restHighLevelClient.close();
    }


}
