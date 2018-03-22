package com.leno.elasticSearch.test;

import com.leno.elasticSearch.ESClientFactory;
import com.leno.elasticSearch.ElConfig;
import com.leno.elasticSearch.School;
import com.leno.elasticSearch.annotation.MyFieldType;
import com.leno.elasticSearch.annotation.MyType;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TestMapping {

    public static void main(String[] args) throws Exception{
        createIndexMapping();

        close();

    }

    public static void createIndexMapping(){
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();

        CreateIndexRequest request = new CreateIndexRequest(School.class.getAnnotation(MyType.class).indexName());
        request.settings(Settings.builder()
                .put(ElConfig.NUMBER_OF_SHARDS, 3)
                .put(ElConfig.NUMBER_OF_REPLICAS, 1));

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
        Map map6 = new HashMap();
        map6.put(ElConfig.EL_TYPE, ElConfig.EL_BOOLEAN);
        Map map7 = new HashMap();
        map7.put(ElConfig.EL_TYPE, ElConfig.EL_DATE);
        Map map8 = new HashMap();
        map8.put(ElConfig.EL_TYPE, ElConfig.El_KEYWORD);
        Map map9= new HashMap();
        map9.put(ElConfig.EL_TYPE, ElConfig.El_LONG);

        mapType.put(ElConfig.El_STRING, map3);
        mapType.put(ElConfig.El_INTEGER, map4);
        mapType.put(ElConfig.El_DOUBLE, map5);
        mapType.put(ElConfig.EL_BOOLEAN, map6);
        mapType.put(ElConfig.EL_DATE, map7);
        mapType.put(ElConfig.El_KEYWORD, map8);
        mapType.put(ElConfig.El_LONG, map9);

        Map map2 = new HashMap();
        Field[] fields = School.class.getDeclaredFields();
        for(Field field : fields) {
            if (field.getAnnotation(MyFieldType.class) == null || StringUtils.isBlank(field.getAnnotation(MyFieldType.class).typeName())) {
                map2.put(field.getName(), mapType.get(ElConfig.El_STRING));
            } else {
                map2.put(field.getName(), mapType.get(field.getAnnotation(MyFieldType.class).typeName()));
            }
        }

        Map map1 = new HashMap();
        map1.put(ElConfig.PROPERTIES, map2);

        Map map = new HashMap();
        map.put(School.class.getAnnotation(MyType.class).typeName(), map1);
        request.mapping(School.class.getAnnotation(MyType.class).typeName(), map);

        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public static void close() throws Exception{
        RestHighLevelClient restHighLevelClient = ESClientFactory.getHighLevelClient();
        restHighLevelClient.close();
    }
}
