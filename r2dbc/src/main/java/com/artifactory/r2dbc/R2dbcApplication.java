package com.artifactory.r2dbc;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class R2dbcApplication {

	public static void main(String[] args) {

		SpringApplication.run(R2dbcApplication.class, args);
	}





}
