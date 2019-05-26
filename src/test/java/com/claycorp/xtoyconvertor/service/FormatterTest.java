package com.claycorp.xtoyconvertor.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatterTest {

    public static void main(String[] args) {
        String dec = "1231.32";
        String intVal = "12345678901";
        /*if(dec.matches("[0-9]+\\.[0-9]+")){
            System.out.println(Double.parseDouble(dec));
        }*/

        if(intVal.matches("[0-9]{1,10}+")){
            System.out.println(Integer.parseInt(intVal));
        }
    }
}
