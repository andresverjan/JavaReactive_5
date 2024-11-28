package com.artifactory.crud.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import javax.swing.plaf.PanelUI;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {


    @Bean
    public RouterFunction<ServerResponse> personRoutes(PersonComponentHandler personComponentHandler) {
        return route()

                .GET("/person/", personComponentHandler::getPersons)
                .GET("/person/{id}", personComponentHandler::getPersonById)
                .POST("/person/", personComponentHandler::create)
                .PUT("/person/", personComponentHandler::update)
                .DELETE("/person/{id}", personComponentHandler::deletePersonByid)
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> estudianteRoutes(EstudianteComponentHandler componentHandler) {
    return route(GET("/estudianteAprobados/"), componentHandler::getEstudianteAprobados)
            .andRoute(GET("/estudianteReprobados/"), componentHandler::getEstudianteReprobados)
            .andRoute(GET("/estudiantes/"), componentHandler::getEstudiante);
    }

    @Bean
    public RouterFunction<ServerResponse> estudianteMateriaRoutes(EstudianteMateriaComponentHandler estudianteMateriaComponentHandler) {
        return route(POST("/estudianteMateria/"), estudianteMateriaComponentHandler::createEstudianteWithMaterias);
    }
    @Bean
    public RouterFunction<ServerResponse> clienteRoutes(ClienteComponentHandler clienteComponentHandler) {
        return route()

                .GET("/cliente/", clienteComponentHandler::getClientes)
                .POST("/cliente/", clienteComponentHandler::create)
                .PUT("/cliente/", clienteComponentHandler::update)
                .DELETE("/cliente/{id}", clienteComponentHandler::deleteClienteByid)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productoRoutes(ProductoComponentHandler productoComponentHandler) {
        return route()


                .GET("/producto/", productoComponentHandler::getProductos)
                .GET("/producto/{id}", productoComponentHandler::getProductoById)
                .POST("/producto/", productoComponentHandler::create)
                .PUT("/producto/", productoComponentHandler::update)
                .DELETE("/producto/{id}", productoComponentHandler::deleteProductoByid)
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> proveedorRoutes(ProveedorComponentHandler proveedorComponentHandler) {
        return route()


                .GET("/proveedor/", proveedorComponentHandler::getProveedor)
                .GET("/proveedor/{id}", proveedorComponentHandler::getProveedoryId)
                .POST("/proveedor/", proveedorComponentHandler::create)
                .PUT("/proveedor/", proveedorComponentHandler::update)
                .DELETE("/proveedor/{id}", proveedorComponentHandler::deleteProveedorByid)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> compraRoutes(CompraComponentHandler compraComponentHandler) {
        return route()
                .GET("/compras/", compraComponentHandler::getCompras)
                .GET("/compras/{id}", compraComponentHandler::getCompraById)
                .POST("/realizarCompra/", compraComponentHandler::realizarCompra)
                .PUT("/actualizarCompras/", compraComponentHandler::actualizarCompra)
                .DELETE("/compras/{id}", compraComponentHandler::deleteCompraByid).build();
    }

    @Bean
    public RouterFunction<ServerResponse> carritoRoutes(CarritoDetalleComponentHandler carritoDetalleComponentHandler) {
        return route(POST("/createcarrito/"), carritoDetalleComponentHandler::crearCarrito)
                .andRoute(GET("/listarCarritos/"), carritoDetalleComponentHandler::getCarritos)
                .andRoute(GET("/calculartotal/{id}"), carritoDetalleComponentHandler::calcularTotalByid)
                .andRoute(PUT("/actualizarProducto/"), carritoDetalleComponentHandler::actualizarProducto)
                .andRoute(DELETE("/borrarCarrito/{id}"), carritoDetalleComponentHandler::deleteCarritoByid)
                .andRoute(DELETE("/borrarProducto/{id}"), carritoDetalleComponentHandler::deleteProductoByid);
    }

    @Bean
    public RouterFunction<ServerResponse> ventaRoutes(VentaComponentHandler ventaComponentHandler) {
        return route(POST("/crearVenta/"), ventaComponentHandler::realizarVenta)
                .andRoute(GET("/listarVentas/"), ventaComponentHandler::getVentas)
                .andRoute(PUT("/actualizarVenta/"), ventaComponentHandler::actualizarVenta)
                .andRoute(DELETE("/borrarVenta/{id}"), ventaComponentHandler::deleteVentaByid);
    }

    @Bean
    public RouterFunction<ServerResponse> ReporteCompra(ReporteCompraComponentHandler reporteCompraComponentHandler){
        return route(GET("/Reportecompras/") ,reporteCompraComponentHandler::getReporteCompras)
                .andRoute(GET("/ReportecomprasProveedor/") ,reporteCompraComponentHandler::getReporteCompraProveedor);

    }
    @Bean
    public RouterFunction<ServerResponse> ReporteVenta(ReporteVentaComponentHandler reporteVentaComponentHandler){
        return route(GET("/ReporteVentas/") ,reporteVentaComponentHandler::getReporteVenta)
                .andRoute(GET("/ReporteTop5Ventas/") ,reporteVentaComponentHandler::getTop5ReporteVenta)
                .andRoute(GET("/ReporteVentasClientes/") ,reporteVentaComponentHandler::getReporteVentaCliente)
                .andRoute(GET("/ReporteVentasClientesCategoria/") ,reporteVentaComponentHandler::getReporteVentaCategoriaFecha);

    }
}