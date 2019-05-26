package com.claycorp.xtoyconvertor.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import java.util.Iterator;
import java.util.List;

public class CsvFlatFileReader<T> {

    private final Iterator<T> data;


    public CsvFlatFileReader(List<T> data) {
        this.data = data.iterator();
    }

    public FlatFileItemReader<T> itemReader(String path, String[] headers) {
        FlatFileItemReader<T> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource(path));
        DefaultLineMapper<T> customMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(headers);

        customMapper.setLineTokenizer(tokenizer);
        // customMapper.setFieldSetMapper(new CustomFieldSetMapper());
        customMapper.afterPropertiesSet();

        return reader;
    }
}
