package curso.java.reactivo.router_function.registro;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class RegistroRouter {
    @Bean
    public RouterFunction<ServerResponse> registroRoutes(RegistroHandler handler) {
        return route()
                .POST("/registro", handler::registrarNota)
                .GET("/registro/aprobados", handler::getEstudiantesAprobados)
                .GET("/registro/reprobados", handler::getEstudiantesReprobados)
                .build();
    }
}
