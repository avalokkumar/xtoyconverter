package com.claycorp.xtoyconvertor.task;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;

@Service
public class ConversionTask implements Tasklet {


    @Override
    public RepeatStatus execute(
            StepContribution contribution, ChunkContext chunkContext) throws Exception {

        return null;
    }
}
