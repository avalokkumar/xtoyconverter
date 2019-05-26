package com.claycorp.xtoyconvertor.config;

import com.claycorp.xtoyconvertor.entity.AppStore;
import com.claycorp.xtoyconvertor.reader.StatelessItemReader;
import com.claycorp.xtoyconvertor.util.PojoUtil;
import com.claycorp.xtoyconvertor.util.XToYConvertorUtil;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
@EnableBatchProcessing
public class ConversionJobConfiguration extends DefaultBatchConfigurer {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PojoUtil pojoUtil;

    @Autowired
    private XToYConvertorUtil convertorUtil;

    private static void write(List items) {
        items.forEach(item -> System.out.println("Current Item " + item));
    }

    @Bean
    public StatelessItemReader statelessItemReader() {
        List<String> items = new ArrayList<>();
        items.add("foo");
        items.add("bar");
        items.add("baz");

        return new StatelessItemReader(items);
    }

    @Bean
    public Job conversionJob() throws NotFoundException, CannotCompileException {

        return jobBuilderFactory.get("conversion")
                .start(appStoreStep())
                .build();
    }

    @Bean
    public Step textConversionStep() {

        return stepBuilderFactory.get("textConversion")
                .<String, String>chunk(2)
                .reader(statelessItemReader())
                .writer(items -> items.forEach(item -> {
                    System.out.println("currentItem " + item);
                }))
                .build();
    }

    @Bean
    public Step appStoreStep() throws NotFoundException, CannotCompileException {
        List<Object> csvData = new ArrayList<>();

        csvData = convertorUtil.getCsvData();
        return stepBuilderFactory.get("appStoreStep")
                .chunk(2)
                .reader(mongoItemReader(new File("asd"), AppStore.class))
                .writer(appStoreItemWriter())
                .build();
    }

    /**
     * Reader to read AppStore values from db
     *
     * @return
     */
    @Bean
    public MongoItemReader<Object> mongoItemReader(File file, Class clss) {
        MongoItemReader<Object> reader = new MongoItemReader<>();
        reader.setTemplate(mongoTemplate);
        reader.setTargetType(clss);
        reader.setQuery("{}");
        reader.setMaxItemCount(10);
        Map<String, Sort.Direction> sorts = new HashMap<>(1);
        sorts.put("status", Sort.Direction.ASC);
        reader.setSort(sorts);

        return reader;
    }


    @Bean
    public ItemWriter appStoreItemWriter() {
        return ConversionJobConfiguration::write;
    }
}
