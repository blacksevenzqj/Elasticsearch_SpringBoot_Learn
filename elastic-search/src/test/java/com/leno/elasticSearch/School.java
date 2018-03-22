package com.leno.elasticSearch;

import com.leno.elasticSearch.annotation.MyFieldType;
import com.leno.elasticSearch.annotation.MyType;
import lombok.Data;

import java.util.Date;

@Data
@MyType(indexName="xiamo", typeName="school", routingName="school")
public class School {

    @MyFieldType(typeName=ElConfig.El_KEYWORD)
    private String articleID;

    @MyFieldType(typeName=ElConfig.El_LONG)
    private long userID;

    @MyFieldType(typeName=ElConfig.EL_BOOLEAN)
    private boolean hidden;

    @MyFieldType(typeName=ElConfig.EL_DATE)
    private Date postDate;

}
