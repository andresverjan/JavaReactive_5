package org.example.finalproject.usuario;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Mono<Usuario> crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Mono<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Flux<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Mono<Usuario> editarUsuario(Integer id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .flatMap(usuario -> {
                    usuario.setNombre(usuarioActualizado.getNombre());
                    usuario.setContacto(usuarioActualizado.getContacto());
                    return usuarioRepository.save(usuario);
                });
    }

    public Mono<Void> eliminarUsuario(Integer id) {
        return usuarioRepository.deleteById(id);
    }
}
