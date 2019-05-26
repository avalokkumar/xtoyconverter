package com.claycorp.xtoyconvertor.util;

import com.google.common.base.CaseFormat;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PojoUtil {

    /*  REGEX EXPLAINED
        ,           // Split on comma
        (?=         // Followed by
           (?:      // Start a non-capture group
             [^"]*  // 0 or more non-quote characters
             "      // 1 quote
             [^"]*  // 0 or more non-quote characters
             "      // 1 quote
           )*       // 0 or more repetition of non-capture group (multiple of 2 quotes will be even)
           [^"]*    // Finally 0 or more non-quotes
           $        // Till the end  (This is necessary, else every comma will satisfy the condition)
        )

     */
    private static final String SPLIT_REGEX = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    public List<Object> loadCsvData(List<String> csvRecord) throws NotFoundException, CannotCompileException {

        List<String> headers = Arrays.asList(csvRecord.get(0).replace("\"", "").split(","));
        List<Class> dataProperties = getDataTypes(Arrays.asList(csvRecord.get(1).split(",")));
        Map<String, Class<?>> props = getProperties(headers, dataProperties);
        Class<?> clazz = PojoGenerator.generate("Pojo$Generated", props);

        return csvRecord.stream()
                .skip(1)
                .map(record -> getData(record, clazz, props))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private Map<String, Class<?>> getProperties(List<String> headers, List<Class> dataProperties) {
        Map<String, Class<?>> properties = new LinkedHashMap<>();
        Iterator<String> headerItr = headers.iterator();
        Iterator<Class> dataTypeItr = dataProperties.iterator();

        while (headerItr.hasNext() && dataTypeItr.hasNext()) {
            String headerText =
                    CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, headerItr.next().replace(".", ""));
            properties.put(headerText, dataTypeItr.next());
        }

        return properties;
    }

    private Object getData(String record, Class clazz, Map<String, Class<?>> props) {
        Object obj = null;
        try {
            obj = clazz.newInstance();

            Iterator<String> propertyNamesItr = props.keySet().iterator();
            Iterator<String> values = getCsvValues(record);

            while (propertyNamesItr.hasNext() && values.hasNext()) {
                String propName = propertyNamesItr.next();
                String value = values.next();
                Class fieldDataType = props.get(propName);

                try {
                    clazz.getMethod("set" + CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, propName),
                            fieldDataType).invoke(obj, parseObjectFromString(value, fieldDataType));
                } catch (Exception noMethodEx) {
                    values.next();
                    log.error(noMethodEx.getMessage());
                }
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        return obj;
    }

    private Iterator<String> getCsvValues(String record) {
        return Arrays.asList(record.split(SPLIT_REGEX)).stream().map(value -> value.replace("\"", "")).iterator();
    }

    private <T> T parseObjectFromString(String s, Class<T> clazz) throws Exception {
        return clazz.getConstructor(new Class[]{String.class}).newInstance(s);
    }

    private List<Class> getDataTypes(List<String> values) {

        return values.stream()
                .map(this::getMatchingDataType)
                .collect(Collectors.toList());
    }

    public Class getMatchingDataType(String value) {

        return Arrays.asList(DataType.values())
                .stream()
                .filter(dataType -> {
                    Pattern pattern = Pattern.compile(dataType.dataTypeRegex);
                    Matcher matcher = pattern.matcher(value);
                    return matcher.matches();
                })
                .map(dataType -> dataType.dataType)
                .map(dataType -> verifyAndGetDataType(dataType, value))
                .findFirst()
                .orElse(String.class);

    }

    private Class verifyAndGetDataType(Class aClass, String value) {
        Class classType;

        try {
            if (aClass.getSimpleName().equals(Integer.class.getSimpleName())) {
                Integer.parseInt(value);
                classType = Integer.class;

            } else if (aClass.getSimpleName().equals(Long.class.getSimpleName())) {
                Integer.parseInt(value);
                classType = Long.class;
            } else if (aClass.getSimpleName().equals(Double.class.getSimpleName())) {
                Double.parseDouble(value);
                classType = Double.class;

            } else if (aClass.getSimpleName().equals(Boolean.class.getSimpleName())) {
                Boolean.parseBoolean(value);
                classType = Boolean.class;
            } else if (aClass.getSimpleName().equals(Character.class.getSimpleName())) {
                Boolean.parseBoolean(value);
                classType = Character.class;
            } else {
                classType = String.class;
            }
        } catch (NumberFormatException nfex) {
            log.error(nfex.getMessage());
            return String.class;
        }

       /* if (aClass.getSimpleName().equals(Integer.class.getSimpleName())) {
            return parseAndVerify(Integer.class, value);
        }*/
        return aClass;
    }

   /* private Class parseAndVerify(Class<?> clss, String value) {
        try {
            if(clss.getSimpleName()){
                Integer.parseInt(value);
            }

        } catch (NumberFormatException nfex) {
            log.error(nfex.getMessage());
            return String.class;
        }
    }*/
}
