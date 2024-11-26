package cedesistemas.edu.co.actividadfinal.interfaces;

import cedesistemas.edu.co.actividadfinal.models.ReportCategories;
import cedesistemas.edu.co.actividadfinal.models.ReportClients;
import cedesistemas.edu.co.actividadfinal.models.ReportPurchaseDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSalesDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSuppliers;
import cedesistemas.edu.co.actividadfinal.models.ReportTopFive;
import reactor.core.publisher.Flux;

public interface ReportsServiceInterface {
    Flux<ReportPurchaseDetails> getReportPurchaseDetails(String fechaInicial, String fechaFinal);

    Flux<ReportSalesDetails> getReportSalesDetails(String fechaInicial, String fechaFinal);

    Flux<ReportTopFive> getReportTopFive(String fechaInicial, String fechaFinal);

    Flux<ReportSuppliers> getSuppliersByIdAndDates(Integer proveedor_id, String fechaInicial, String fechaFinal);

    Flux<ReportClients> getClientsByIdAndDates(Integer cliente_id, String fechaInicial, String fechaFinal);

    Flux<ReportCategories> getCategoriesByNameAndDates(String categoria, String fechaInicial, String fechaFinal);
}
