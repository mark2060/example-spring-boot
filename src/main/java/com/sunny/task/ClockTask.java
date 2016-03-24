package com.sunny.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * ClockTask
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-03-24
 */
@Component
public class ClockTask {

    @Scheduled(cron = "*/5 * * * * ?")
    public void execute() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        //do somethings
        stopWatch.stop();

        System.out.println("ClockTask,execute,spend " + stopWatch.getTotalTimeMillis());
    }

}
