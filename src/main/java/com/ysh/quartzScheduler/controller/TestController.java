package com.ysh.quartzScheduler.controller;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ysh.quartzScheduler.service.MyJobService;

@Controller
public class TestController {

	private MyJobService myJobService;
	private Scheduler scheduler;

	@Autowired
	public void TestController(MyJobService myJobService, Scheduler scheduler) {
		this.myJobService = myJobService;
		this.scheduler = scheduler;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseBody
	public String test() {
		return "test";
	}

	@RequestMapping(value = "/chageCron", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String changeCron() throws SchedulerException {
		String triggerName = "cronTrigger"; //scheduler-context.xml에서 설정한 이름
		int dayInterval = 1;
		TriggerKey triggerKey = new TriggerKey(triggerName);

		// 새 cron 표현식: 매 N일마다 아침 6시 (N = dayInterval)
		// String cronExpr = "0 0 6 1/" + dayInterval + " * ?";
		String cronExpr = "0/1 * * * * ?";

		CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)).forJob("myJobDetail") // JobDetail의 이름과 일치해야 함
				.build();

		scheduler.rescheduleJob(triggerKey, newTrigger);

		return "스케줄이 " + dayInterval + "일 주기로 변경되었습니다.";
	}

	@RequestMapping(value = "/currentSchedule", produces="application/json;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String getCurrentCron() throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey("cronTrigger"); //scheduler-contex.xml에서 설정한 이름

		Trigger trigger = scheduler.getTrigger(triggerKey);
		if (trigger instanceof CronTrigger) {
			CronTrigger cronTrigger = (CronTrigger) trigger;
			String cronExpression = cronTrigger.getCronExpression();
			return "현재 크론식: " + cronExpression;
		} else {
			return "해당 트리거는 CronTrigger가 아닙니다.";
		}
	}

}
