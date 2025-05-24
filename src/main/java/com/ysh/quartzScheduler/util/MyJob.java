package com.ysh.quartzScheduler.util;

import org.springframework.stereotype.Component;

import com.ysh.quartzScheduler.service.MyJobService;

// 실제 실행할 Job

@Component
public class MyJob {
    private MyJobService myJobService;

    // Spring이 setter를 통해 의존성 주입합니다
    public void setMyJobService(MyJobService myJobService) {
        this.myJobService = myJobService;
    }

    // Quartz가 호출할 메서드
    public void execute() {
        myJobService.collectData();
    }
}
