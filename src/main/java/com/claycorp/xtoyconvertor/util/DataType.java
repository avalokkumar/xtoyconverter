package com.claycorp.xtoyconvertor.util;

import java.util.Date;

enum DataType {
    INT("[0-9]{0,10}", Integer.class),
    LONG("[0-9]+", Long.class),
    DOUBLE("[0-9]+\\.[0-9]+", Double.class),
    BOOLEAN("(true|false)", Boolean.class),
    CHAR("[A-Za-z0-9]", Character.class),
    DATE("(\\d{2,4}-\\d{1,2}-\\d{1,2})|(\\d{2,4}\\/\\d{1,2}\\/\\d{1,2})", Date.class);

    String dataTypeRegex;
    Class dataType;

    DataType(String dataTypeRegex, Class dataType) {
        this.dataTypeRegex = dataTypeRegex;
        this.dataType = dataType;
    }
}