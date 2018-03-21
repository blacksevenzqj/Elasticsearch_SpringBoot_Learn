package com.leno.elasticSearch;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Test2 {

    public static void main(String[] args) throws Exception{
//        createIndex();
//
//
//
//        close();

       System.out.println(School.class.getAnnotation(MyTypeAbc.class));

        Field[] fields = School.class.getDeclaredFields();
        for(Field field : fields){
            if(field.getType() == int.class || field.getType() == Integer.class){
                System.out.println(field.getType());
            }else if(field.getType() == String.class){
                System.out.println(field.getName());
            }
        }

        System.out.println(int.class == Integer.class);

    }

    public static void createIndex(){
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();

        CreateIndexRequest request = new CreateIndexRequest("xiamo");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 1));

//        request.mapping("school",
//                         "{\n" +
//                                 "    \"school\":{\n" +
//                                 "      \"properties\": {\n" +
//                                 "        \"message\":{\n" +
//                                 "          \"type\":\"text\"\n" +
//                                 "        }\n" +
//                                 "      }\n" +
//                                 "    }\n" +
//                                 "  }",
//                XContentType.JSON);


        Map map3 = new HashMap();
        map3.put("type","text");
        Map map4 = new HashMap();
        map4.put("type","integer");

        Map map2 = new HashMap();
        Field[] fields = School.class.getDeclaredFields();
        for(Field field : fields){
            if(field.getType() == int.class || field.getType() == Integer.class){
                map2.put(field.getName(), map4);
            }else if(field.getType() == String.class){
                map2.put(field.getName(), map3);
            }
            System.out.println(field.getName());
        }

        Map map1 = new HashMap();
        map1.put("properties", map2);

        Map map = new HashMap();
        map.put(School.class.getAnnotation(MyTypeAbc.class).typeName(), map1);
        request.mapping(School.class.getAnnotation(MyTypeAbc.class).typeName(), map);
        try {
            restHighLevelClient.indices().create(request);
        }catch (Exception e){
            e.printStackTrace();
        }

    }













    public static void close() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        restHighLevelClient.close();
    }
}
