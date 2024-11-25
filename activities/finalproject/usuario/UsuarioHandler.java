package org.example.finalproject.usuario;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UsuarioHandler {

    private final UsuarioService usuarioService;

    public UsuarioHandler(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public Mono<ServerResponse> crearUsuario(ServerRequest request) {
        return request.bodyToMono(Usuario.class)
                .flatMap(usuarioService::crearUsuario)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> buscarPorId(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return usuarioService.buscarPorId(id)
                .flatMap(ServerResponse.ok()::bodyValue)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> listarUsuarios(ServerRequest request) {
        return ServerResponse.ok().body(usuarioService.listarUsuarios(), Usuario.class);
    }

    public Mono<ServerResponse> editarUsuario(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return request.bodyToMono(Usuario.class)
                .flatMap(usuario -> usuarioService.editarUsuario(id, usuario))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> eliminarUsuario(ServerRequest request) {
        Integer id = Integer.valueOf(request.pathVariable("id"));
        return usuarioService.eliminarUsuario(id)
                .then(ServerResponse.noContent().build());
    }
}
