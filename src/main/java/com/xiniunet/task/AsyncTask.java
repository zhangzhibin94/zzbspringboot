package com.xiniunet.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步任务
 */
@Component
public class AsyncTask {
    @Async
    public void task1() throws InterruptedException {
        long startTime = System.currentTimeMillis();//当前时间
        Thread.sleep(1000l);
        long endTime = System.currentTimeMillis();
        System.out.println("task1耗时："+(endTime-startTime));
    }
    @Async
    public void task2() throws InterruptedException {
        long startTime = System.currentTimeMillis();//当前时间
        Thread.sleep(3000l);
        long endTime = System.currentTimeMillis();
        System.out.println("task1耗时："+(endTime-startTime));
    }
    @Async
    public void task3() throws InterruptedException {
        long startTime = System.currentTimeMillis();//当前时间
        Thread.sleep(4000l);
        long endTime = System.currentTimeMillis();
        System.out.println("task1耗时："+(endTime-startTime));
    }
}
