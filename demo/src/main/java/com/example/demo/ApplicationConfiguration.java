package com.example.demo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;

import java.nio.CharBuffer;
class ApplicationConfiguration {
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
}