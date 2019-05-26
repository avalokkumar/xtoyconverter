package com.claycorp.xtoyconvertor.model;

import lombok.Data;

@Data
public class SortCriteria {

    private SortOrder order;
    private String sortAttribute;
}


enum SortOrder {
    ASC("asc"), DESC("desc");

    SortOrder(String order) {
        this.order = order;
    }

    private String order;

    public String getOrder() {
        return this.order;
    }
}