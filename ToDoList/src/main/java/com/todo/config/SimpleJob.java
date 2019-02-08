package com.todo.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.todo.service.IEventService;

public class SimpleJob implements Job {
	@Autowired
	IEventService ievent;
	
	Logger logger=LoggerFactory.getLogger(SchedulerConfig.class);


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("Job Execution started...!");
		ievent.eventAlert();
		ievent.statusChangeFromNew();
		ievent.statusChangeFromRemaining();
//		ievent.deleteCompletedEvent();    // DELETE COMPLETED EVENTS
		logger.info("Job Execution finished...!");

		
	}

}
