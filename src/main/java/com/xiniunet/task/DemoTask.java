package com.xiniunet.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时任务demo
 */
@Component
public class DemoTask {
//    @Scheduled(fixedRate = 1000)//1000ms
    public void printDateTask(){
        System.out.println("当前时间"+new Date());
    }

    /**
     * cron表达式在线生成http://cron.qqe2.com/
     */
    @Scheduled(cron = "0 0 1 * * ?")//每日凌晨1点执行
    public void cronTask(){

    }
}
