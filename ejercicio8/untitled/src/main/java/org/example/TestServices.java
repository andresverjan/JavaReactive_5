package org.example;

import io.r2dbc.spi.ConnectionFactory;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class TestServices {
	private static final Logger log = LoggerFactory.getLogger(TestServices.class);

	public Mono<Publisher<Integer>> testConnection() {
			return Mono.from(factory.create())
				.flatMap(connection -> Mono.from(
								connection.createStatement("SELECT 1 from dual").execute())
						.map(result -> result.map((row, rowMetadata) -> row.get(0, Integer.class)))
						.doOnNext(
								result -> log.info("Connection exitosa"
								))
						.doFinally(
								signalType -> connection.close()
						));
	}

	private static ConnectionFactory factory = null;
	public TestServices(ConnectionFactory factory) {
		this.factory = factory;{

	}

	}
}