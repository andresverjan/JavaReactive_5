package com.example.demo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import java.nio.CharBuffer;
@Configuration
@EnableR2dbcRepositories
class ApplicationConfiguration extends AbstractR2dbcConfiguration {
	@Override
	public ConnectionFactory connectionFactory() {
		return ConnectionFactories.get(
				ConnectionFactoryOptions.builder()
						.option(ConnectionFactoryOptions.DRIVER, "oracle")
						.option(ConnectionFactoryOptions.HOST, "
		return null;
	}
	/*@Bean
	public static ConnectionFactory getConnectionFactory() {
		return ConnectionFactories.get(
				ConnectionFactoryOptions.builder()
						.option(ConnectionFactoryOptions.DRIVER, "oracle")
						.option(ConnectionFactoryOptions.HOST, "10.8.72.157")
						.option(ConnectionFactoryOptions.PORT, 59127)
						.option(ConnectionFactoryOptions.DATABASE, "AUTHEDDB")
						.option(ConnectionFactoryOptions.USER, "SCHAUTAD")
						.option(ConnectionFactoryOptions.PASSWORD, CharBuffer.wrap("Hvp62]eHt2N?"))
						.build());
	}

	@Bean
	public static ConnectionFactory getConnectionFactory() {
		return ConnectionFactories.get(
				ConnectionFactoryOptions.builder()
						.option(ConnectionFactoryOptions.DRIVER, "oracle")
						.option(ConnectionFactoryOptions.HOST, "10.8.72.157")
						.option(ConnectionFactoryOptions.PORT, 59127)
						.option(ConnectionFactoryOptions.DATABASE, "AUTHEDDB")
						.option(ConnectionFactoryOptions.USER, "SCHAUTAD")
						.option(ConnectionFactoryOptions.PASSWORD, CharBuffer.wrap("Hvp62]eHt2N?"))
						.build());
	}*/
}