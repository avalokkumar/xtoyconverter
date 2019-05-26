package com.claycorp.xtoyconvertor.mapper;

import com.claycorp.xtoyconvertor.entity.AppStore;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomFieldSetMapper implements FieldSetMapper<AppStore> {

    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public AppStore mapFieldSet(FieldSet fieldSet) throws BindException {
        return new AppStore(
                fieldSet.readLong(counter.incrementAndGet()),
                fieldSet.readString("id"),
                fieldSet.readString("trackName"),
                fieldSet.readLong("sizeInBytes"),
                fieldSet.readString("currency"),
                fieldSet.readDouble("price"),
                fieldSet.readLong("ratingTotalCount"),
                fieldSet.readLong("ratingCountVersion"),
                fieldSet.readDouble("userRating"),
                fieldSet.readDouble("userRatingVersion"),
                fieldSet.readString("version"),
                fieldSet.readString("contRating"),
                fieldSet.readString("primeGenre"),
                fieldSet.readLong("supDevicesNum"),
                fieldSet.readLong("iPadScUrlsNum"),
                fieldSet.readLong("langNum"),
                fieldSet.readLong("vppLic")
        );
    }
}