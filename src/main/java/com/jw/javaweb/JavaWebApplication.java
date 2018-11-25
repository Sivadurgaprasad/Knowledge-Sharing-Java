package com.jw.javaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.jw.config.WebConfig;

@SpringBootApplication(scanBasePackages = { "com.jw.controller", "com.jw.service", "com.jw.util", "com.jw.exception" })
@EnableMongoRepositories(basePackages = { "com.jw.repository" })
@Import(value = { WebConfig.class })
public class JavaWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaWebApplication.class, args);
	}
}
