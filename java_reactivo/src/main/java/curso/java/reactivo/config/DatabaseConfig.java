// Configuration class to define the ConnectionFactory bean
package curso.java.reactivo.config;

import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.Result;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class DatabaseConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get("r2dbc:postgresql://root:root@localhost:5500/java_reactivo");
    }
    @Bean
    public CommandLineRunner testConnection(ConnectionFactory connectionFactory) {
        return args -> {
            Mono.from(connectionFactory.create())
                    .flatMapMany(connection -> connection.createStatement("SELECT 1")
                            .execute())
                    .flatMap(Result::getRowsUpdated)
                    .doOnNext(count -> System.out.println("Connection successful, test query returned: " + count))
                    .doOnError(error -> System.err.println("Connection failed: " + error.getMessage()))
                    .subscribe();
        };
    }
}