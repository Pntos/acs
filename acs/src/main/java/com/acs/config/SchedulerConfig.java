package com.acs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;

@Configuration
public class SchedulerConfig implements AsyncConfigurer, SchedulingConfigurer {
	private final int POOL_SIZE = 10;
	
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(POOL_SIZE);
		scheduler.setPoolSize(Runtime.getRuntime().availableProcessors() * 2);
		scheduler.setThreadNamePrefix("MY-SCHEDULER-TASK-POOL"); scheduler.initialize();
		return scheduler; 
	}
	
	@Override public Executor getAsyncExecutor() { 
		return
		this.threadPoolTaskScheduler(); 
	}
	
	@Override public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(this.threadPoolTaskScheduler()); 
	}
	
}
