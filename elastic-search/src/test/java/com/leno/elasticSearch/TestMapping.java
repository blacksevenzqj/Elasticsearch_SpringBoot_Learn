package com.leno.elasticSearch;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestMapping {

    public static void main(String[] args) throws Exception{
        createIndex();


        close();

        School school = new School();
        System.out.println(school.getClass().getAnnotation(MyTypeAbc.class).typeName());
        System.out.println(School.class.getAnnotation(MyTypeAbc.class).typeName());

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

        CreateIndexRequest request = new CreateIndexRequest(School.class.getAnnotation(MyTypeAbc.class).indexName());
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


        Map mapType = new HashMap();
        Map map3 = new HashMap();
        map3.put(ElConfig.EL_TYPE, ElConfig.El_STRING);
        Map map4 = new HashMap();
        map4.put(ElConfig.EL_TYPE, ElConfig.El_INTEGER);
        Map map5 = new HashMap();
        map5.put(ElConfig.EL_TYPE, ElConfig.El_DOUBLE);
        mapType.put(ElConfig.El_STRING, map3);
        mapType.put(ElConfig.El_INTEGER, map4);
        mapType.put(ElConfig.El_DOUBLE, map5);

        Map map2 = new HashMap();
        Field[] fields = School.class.getDeclaredFields();
        for(Field field : fields){
            if(field.getType() == int.class || field.getType() == Integer.class){
                map2.put(field.getName(), mapType.get(ElConfig.El_INTEGER));
            }else if(field.getType() == String.class){
                map2.put(field.getName(), mapType.get(ElConfig.El_STRING));
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
