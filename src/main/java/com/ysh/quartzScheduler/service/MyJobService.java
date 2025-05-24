package com.ysh.quartzScheduler.service;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyJobService {

	@Autowired
	private Scheduler scheduler;

	private static final String JOB_NAME = "myJob";
	private static final String TRIGGER_NAME = "myTrigger";

	public void collectData() {
		System.out.println("Quartz 실행됨: 데이터 수집 로직 수행");
		// 예: API 호출 → DB 저 (실제 비즈니스 로직)
	}


}
