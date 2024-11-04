package org.example;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.nio.CharBuffer;

@Configuration
class ApplicationConfiguration {
	/*@Bean
	public static ConnectionFactory getConnectionFactory() {
		System.out.println("getConnectionFactory");
		return ConnectionFactories.get(
				ConnectionFactoryOptions.builder()
						.option(ConnectionFactoryOptions.DRIVER, "oracle")
						.option(ConnectionFactoryOptions.HOST, "")
						.option(ConnectionFactoryOptions.PORT, 0)
						.option(ConnectionFactoryOptions.DATABASE, "")
						.option(ConnectionFactoryOptions.USER, "")
						.option(ConnectionFactoryOptions.PASSWORD, CharBuffer.wrap(""))
						.build());
	}*/

@Bean
public static ConnectionFactoryInitializer getConnectionFactory(ConnectionFactory connectionFactory) {
	System.out.println("getConnectionFactoryInitializer");
	ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
	initializer.setConnectionFactory(connectionFactory);
	initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
	return initializer;
}

}