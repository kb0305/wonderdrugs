package com.example.Wonderdrug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class })
@EnableScheduling
public class WonderdrugApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonderdrugApplication.class, args);
	}

}
