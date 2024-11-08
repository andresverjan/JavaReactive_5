package curso.java.reactivo.router_function.person;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
@RequiredArgsConstructor
public class PersonRouter {
    @Bean
    public RouterFunction<ServerResponse> personRoutes(PersonHandler handler) {
        return route()
                .GET("/f/person", handler::getPerson)
                .GET("/f/person/{id}", handler::getPersonById)
                .POST("/f/person", handler::addPerson)
                .PUT("/f/person/{id}", handler::updatePerson)
                .build();
    }

}
