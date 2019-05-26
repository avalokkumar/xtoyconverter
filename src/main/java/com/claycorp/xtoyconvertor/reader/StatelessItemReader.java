package com.claycorp.xtoyconvertor.reader;

import org.springframework.batch.item.ItemReader;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class StatelessItemReader implements ItemReader<String> {

    private final Iterator<String> data;


    public StatelessItemReader(List<String> data) {
        this.data = data.iterator();
    }

    @Override
    public String read() throws Exception {

        return Optional.ofNullable(this.data)
                .filter(Iterator::hasNext)
                .map(Iterator::next)
                .orElse(null);
    }
}
