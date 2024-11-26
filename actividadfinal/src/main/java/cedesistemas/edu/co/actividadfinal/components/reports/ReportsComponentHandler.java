package cedesistemas.edu.co.actividadfinal.components.reports;

import cedesistemas.edu.co.actividadfinal.interfaces.ReportsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.ReportCategories;
import cedesistemas.edu.co.actividadfinal.models.ReportClients;
import cedesistemas.edu.co.actividadfinal.models.ReportPurchaseDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSalesDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSuppliers;
import cedesistemas.edu.co.actividadfinal.models.ReportTopFive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ReportsComponentHandler {
    private final ReportsServiceInterface reportsServiceInterface;

    public Mono<ServerResponse> getReportPurchaseDetails(ServerRequest request) {
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getReportPurchaseDetails(fechaInicial, fechaFinal), ReportPurchaseDetails.class);
    }

    public Mono<ServerResponse> getReportSalesDetails(ServerRequest request) {
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getReportSalesDetails(fechaInicial, fechaFinal), ReportSalesDetails.class);
    }

    public Mono<ServerResponse> getReportTopFive(ServerRequest request) {
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getReportTopFive(fechaInicial, fechaFinal), ReportTopFive.class);
    }

    public Mono<ServerResponse> getSuppliersByIdAndDates(ServerRequest request) {
        var proveedor_id = Integer.parseInt(request.queryParam("proveedor_id").orElse("0"));
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getSuppliersByIdAndDates(proveedor_id, fechaInicial, fechaFinal), ReportSuppliers.class);
    }

    public Mono<ServerResponse> getClientsByIdAndDates(ServerRequest request) {
        var cliente_id = Integer.parseInt(request.queryParam("cliente_id").orElse("0"));
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getClientsByIdAndDates(cliente_id, fechaInicial, fechaFinal), ReportClients.class);
    }

    public Mono<ServerResponse> getCategoriesByNameAndDates(ServerRequest request) {
        var categoria = request.queryParam("categoria").orElse("");
        var fechaInicial = request.queryParam("fechaInicial").orElse("");
        var fechaFinal = request.queryParam("fechaFinal").orElse("");
        return ServerResponse.ok().body(reportsServiceInterface.getCategoriesByNameAndDates(categoria, fechaInicial, fechaFinal), ReportCategories.class);
    }
}
