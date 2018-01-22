package com.slauriano.sample.forum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** 
 * Main class to start the application
 * 
 * 
 * @author slauriano
 * 
 * 
 * */
@SpringBootApplication
public class SampleForumApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SampleForumApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SampleForumApplication.class, args);
	}
}
