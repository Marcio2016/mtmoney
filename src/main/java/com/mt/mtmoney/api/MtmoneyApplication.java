package com.mt.mtmoney.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mt.mtmoney.api.config.property.MtmoneyProperty;

@SpringBootApplication
@EnableConfigurationProperties(MtmoneyProperty.class)
public class MtmoneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtmoneyApplication.class, args);
	}

}
