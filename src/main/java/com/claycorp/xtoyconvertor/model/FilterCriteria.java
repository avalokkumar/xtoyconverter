package com.claycorp.xtoyconvertor.model;

import lombok.Data;

import java.util.List;

@Data
public class FilterCriteria {

    private List<String> fields;

    private int offset;

    private int limit;
}
