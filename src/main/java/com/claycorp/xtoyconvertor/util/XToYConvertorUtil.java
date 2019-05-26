package com.claycorp.xtoyconvertor.util;

import javassist.CannotCompileException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class XToYConvertorUtil {

    private PojoUtil pojoUtil;

    XToYConvertorUtil(PojoUtil pojoUtil){
        this.pojoUtil = pojoUtil;
    }
    public List<Object> getCsvData() throws NotFoundException, CannotCompileException {
        Path filePath = Paths.get("src/main/resources/dataset/AppleStore.csv");
        List<String> csvRecord = new ArrayList<>();
        List<Object> csvData = new ArrayList<>();

        try{
            csvRecord = Files.readAllLines(filePath);
        }catch (IOException ie){
            log.error(ie.getMessage());
        }

        if(csvRecord.size() > 1){
            csvData = pojoUtil.loadCsvData(csvRecord);
        }
        return csvData;
    }


}
