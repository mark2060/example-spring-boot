package com.sunny.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ClockTask
 *
 * @author sunny
 * @version 1.0.0
 * @since 2016-03-24
 */
@Component
public class ClockTask {

    @Scheduled(cron = "*/10 * * * * ?")
    public void execute() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();
        //do somethings
        stopWatch.stop();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("ClockTask,execute,current time is  " + format.format(new Date()));
    }

}
