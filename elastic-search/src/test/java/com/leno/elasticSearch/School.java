package com.leno.elasticSearch;

import lombok.Data;

@Data
@MyTypeAbc(indexName="xiamo", typeName="feiji")
public class School {

    private String name;

    private Integer age;


}
