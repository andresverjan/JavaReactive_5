package com.example.demo;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class DemoApplication {
	private static final Logger log = LoggerFactory.getLogger(DemoApplication.class);
	private static ConnectionFactory factory = null;
	public static void main(String[] args) {
		ConnectionFactory factory = ApplicationConfiguration.getConnectionFactory();
		DemoApplication app = new DemoApplication(factory);
		app.testConnection().block();
	}
	public DemoApplication(ConnectionFactory factory) {
		this.factory = factory;
	}

	public Mono<Void> testConnection() {
		return Mono.from(factory.create())
				.flatMap(connection -> Mono.from(
						connection.createStatement("SELECT 1 from dual").execute())
								.map(result -> result.map((row, rowMetadata) -> row.get(0, Integer.class)))
						.doOnNext(result -> log.info("Connection exitosa"))
						.doFinally(signalType -> connection.close())).then();
	}

}