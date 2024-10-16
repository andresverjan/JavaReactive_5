package com.example.demo;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@ExtendWith(SpringExtension.class)
//@SpringBootTest
class DemoApplicationTests {
	@Test
	void testConnection() {
		ConnectionFactory connectionFactory = ApplicationConfiguration.getConnectionFactory();
		DemoApplication demoApplication = new DemoApplication(connectionFactory);
		StepVerifier.create(demoApplication.testConnection())
				.verifyComplete();
	}

}
