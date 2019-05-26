package com.claycorp.xtoyconvertor.service;

import org.apache.commons.lang3.ObjectUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class DemoTest {

    @Test
    public void testDataType() throws ClassNotFoundException {

        String intVal = "1234567890";
        String longVal = "12345678901231";
        String doubleVal = "1234567890.1231";
        String booleanVal = "true";
        String dateVal = "1990/08/07";
        String charVal = "A";
        /*String []record = {"abc_def", "hello_Def_ghj", "HI_Dude"};
        String rec = "hello_Def_ghj";
        String SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";*/
        //Arrays.stream(record).map(WordUtils::uncapitalize).forEach(System.out::println);
        //System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, rec));
        //Class<?> act = Class.forName(intVal);

        //System.out.println(Integer.class.getSimpleName());

        System.out.println(getMatchingDataType(intVal).getSimpleName());
        System.out.println(getMatchingDataType(longVal).getSimpleName());
        System.out.println(getMatchingDataType(doubleVal).getSimpleName());
        System.out.println(getMatchingDataType(booleanVal).getSimpleName());
        System.out.println(getMatchingDataType(dateVal).getSimpleName());
        System.out.println(getMatchingDataType(charVal).getSimpleName());

       /* String val = "\"3\",\"281940292\",\"WeatherBug - Local Weather, Radar, Maps, Alerts\",100524032,\"USD\",0," +
                "188583,2822,3.5,4.5,\"5.0.0\",\"4+\",\"Weather\",37,5,3,1";

        for (String s: val.split(SPLIT_REGEX)) {
            System.out.println(s);
        }*/
    }

    private Class getMatchingDataType(String val) {
        Class dataTypeClass = null;
        try {
            if (ObjectUtils.isNotEmpty(Integer.valueOf(val))) {
                return Integer.class;
            } else if (ObjectUtils.isNotEmpty(Long.valueOf(val))) {
                return Long.class;
            } else if (ObjectUtils.isNotEmpty(Long.valueOf(val))) {
                return Double.class;
            } else if (ObjectUtils.isNotEmpty(Long.valueOf(val))) {
                return Boolean.class;
            } else if (ObjectUtils.isNotEmpty(Long.valueOf(val))) {
                return Character.class;
            } else {
                return String.class;
            }
        } catch (NumberFormatException ignore) {
            System.out.println("failed");
        }
        return String.class;
    }

}


