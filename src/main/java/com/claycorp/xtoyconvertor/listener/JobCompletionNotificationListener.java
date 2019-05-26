package com.claycorp.xtoyconvertor.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        ExecutionContext context = jobExecution.getExecutionContext();
        //TODO: log details before exiting job
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("Job Compeleted: Wrap UP");
        }
    }

}
