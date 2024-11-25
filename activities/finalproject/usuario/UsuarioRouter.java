package org.example.finalproject.usuario;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class UsuarioRouter {

    @Bean
    public RouterFunction<ServerResponse> usuarioRoutes(UsuarioHandler handler) {
        return route(POST("/api/usuarios"), handler::crearUsuario)
                .andRoute(GET("/api/usuarios/{id}"), handler::buscarPorId)
                .andRoute(GET("/api/usuarios"), handler::listarUsuarios)
                .andRoute(PUT("/api/usuarios/{id}"), handler::editarUsuario)
                .andRoute(DELETE("/api/usuarios/{id}"), handler::eliminarUsuario);
    }
}
