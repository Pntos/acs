package com.acs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling	/* @Scheduled 활성화 */
@EnableAsync		/* @Async 활성화 */
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
