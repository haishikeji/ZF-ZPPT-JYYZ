package com.px.employmentsite.jobs;

import com.px.employmentsite.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class testJob {
    @Autowired
    private AppConfig appConfig;

    @Scheduled(cron="${appconfig.jobconfig.class-cron}")
    public void syncClassData() {
        if (appConfig.jobconfig_isRunJob) {
            log.info("开始");
            try {

            } catch (Exception e) {
                log.info("数据错误：" + e.getMessage());
            }
            log.info("结束");
        }
    }

}
