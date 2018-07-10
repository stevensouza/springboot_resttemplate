package com.stevesouza.resttemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * note @SpringBootApplication annotation also defines @Coniguration so you could define spring beans in this class by
 * having a method and annotating it with @Bean.
 */
@SpringBootApplication
public class ResttemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResttemplateApplication.class, args);
	}
}
