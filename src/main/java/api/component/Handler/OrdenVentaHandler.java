package api.component.Handler;
import api.model.Cliente;
import api.model.OrdenVenta;
import api.service.OrdenVentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class OrdenVentaHandler {
    private final OrdenVentaService ordenVentaService;

    public Mono<ServerResponse> getOrdenesVenta(ServerRequest request){
        Flux<OrdenVenta> ordenes = ordenVentaService.getOrdenes();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ordenes,OrdenVenta.class);
    }
    // Listar contenido de la orden de venta
    public Mono<ServerResponse> listarContenidoOrdenVenta(ServerRequest request) {
        Long ordenVentaId = Long.valueOf(request.pathVariable("ordenVentaId"));

        return ordenVentaService.listarContenidoOrdenVenta(ordenVentaId)
                .flatMap(contenido -> ServerResponse.ok().bodyValue(contenido))
                .onErrorResume(e -> {
                    e.printStackTrace(); // Para depurar errores en el servidor
                    return ServerResponse.badRequest().bodyValue(e.getMessage());
                });
    }
}
