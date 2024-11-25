package api.component.Config;
import api.component.Handler.ClienteHandler;
import api.component.Handler.ReporteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
@Configuration
public class ReportesConfig {
    @Bean
    public RouterFunction<ServerResponse> reporteRoutes(ReporteHandler reporteHandler) {
        return route()
                // Reporte de compras en un intervalo de tiempo
                .GET("/reporte/compras", reporteHandler::reporteCompras)
                // Reporte de ventas en un intervalo de tiempo
                .GET("/reporte/ventas", reporteHandler::reporteVentas)
                // Reporte de compras por proveedor
                .GET("/reporte/compras/proveedor", reporteHandler::reporteComprasPorProveedor)
                // Reporte de ventas por cliente
                .GET("/reporte/ventas/cliente", reporteHandler::reporteVentasPorCliente)
                .build();
    }
}
