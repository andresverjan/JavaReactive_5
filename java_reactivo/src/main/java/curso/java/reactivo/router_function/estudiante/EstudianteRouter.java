package curso.java.reactivo.router_function.estudiante;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class EstudianteRouter {
    @Bean
    public RouterFunction<ServerResponse> estudianteRoutes(EstudianteHandler handler) {
        return route()
                .GET("/estudiantes", handler::findAll)
                .GET("/estudiantes/{id}", handler::findById)
                .POST("/estudiantes", handler::save)
                .PUT("/estudiantes/{id}", handler::update)
                .DELETE("/estudiantes/{id}", handler::deleteById)
                .build();
    }
}
