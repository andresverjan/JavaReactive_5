package curso.java.reactivo.router_function.materia;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class MateriaRouter {
    @Bean
    public RouterFunction<ServerResponse> materiaRoutes(MateriaHandler handler) {
        return route()
                .GET("/f/materia", handler::getMateria)
                .GET("/f/materia/{id}", handler::getMateriaById)
                .POST("/f/materia", handler::addMateria)
                .PUT("/f/materia/{id}", handler::updateMateria)
                .build();
    }
}
