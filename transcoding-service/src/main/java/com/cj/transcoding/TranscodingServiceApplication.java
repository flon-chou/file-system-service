package com.cj.transcoding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class TranscodingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TranscodingServiceApplication.class, args);
	}

}
