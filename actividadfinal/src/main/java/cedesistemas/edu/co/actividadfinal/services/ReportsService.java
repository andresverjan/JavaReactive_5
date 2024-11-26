package cedesistemas.edu.co.actividadfinal.services;

import cedesistemas.edu.co.actividadfinal.exceptions.NotFoundException;
import cedesistemas.edu.co.actividadfinal.interfaces.ReportsServiceInterface;
import cedesistemas.edu.co.actividadfinal.models.ReportCategories;
import cedesistemas.edu.co.actividadfinal.models.ReportClients;
import cedesistemas.edu.co.actividadfinal.models.ReportPurchaseDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSalesDetails;
import cedesistemas.edu.co.actividadfinal.models.ReportSuppliers;
import cedesistemas.edu.co.actividadfinal.models.ReportTopFive;
import cedesistemas.edu.co.actividadfinal.repositories.ReportCategoriesRepository;
import cedesistemas.edu.co.actividadfinal.repositories.ReportClientsRepository;
import cedesistemas.edu.co.actividadfinal.repositories.ReportPurchaseDetailsRepository;
import cedesistemas.edu.co.actividadfinal.repositories.ReportSalesDetailsRepository;
import cedesistemas.edu.co.actividadfinal.repositories.ReportSuppliersRepository;
import cedesistemas.edu.co.actividadfinal.repositories.ReportTopFiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ReportsService implements ReportsServiceInterface {

    private final ReportPurchaseDetailsRepository reportPurchaseDetailsRepository;
    private final ReportSalesDetailsRepository reportSalesDetailsRepository;
    private final ReportTopFiveRepository reportTopFiveRepository;
    private final ReportSuppliersRepository reportSuppliersRepository;
    private final ReportClientsRepository reportClientsRepository;
    private final ReportCategoriesRepository reportCategoriesRepository;

    @Override
    public Flux<ReportPurchaseDetails> getReportPurchaseDetails(String fechaInicial, String fechaFinal) {
        return reportPurchaseDetailsRepository.findAllByFechaOrdenBetween(fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No purchases found")));
    }

    @Override
    public Flux<ReportSalesDetails> getReportSalesDetails(String fechaInicial, String fechaFinal) {
        return reportSalesDetailsRepository.findAllByFechaOrdenBetween(fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No sales found")));
    }

    @Override
    public Flux<ReportTopFive> getReportTopFive(String fechaInicial, String fechaFinal) {
        return reportTopFiveRepository.findTopFiveByFechaOrdenBetween(fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No top five found")));
    }

    @Override
    public Flux<ReportSuppliers> getSuppliersByIdAndDates(Integer proveedor_id, String fechaInicial, String fechaFinal) {
        return reportSuppliersRepository.findAllByProveedorIdAndFechaOrdenBetween(proveedor_id, fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No suppliers found")));
    }

    @Override
    public Flux<ReportClients> getClientsByIdAndDates(Integer cliente_id, String fechaInicial, String fechaFinal) {
        return reportClientsRepository.findAllByClienteIdAndFechaOrdenBetween(cliente_id, fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No clients found")));
    }

    @Override
    public Flux<ReportCategories> getCategoriesByNameAndDates(String categoria, String fechaInicial, String fechaFinal) {
        return reportCategoriesRepository.findAllByCategoriaAndFechaOrdenBetween(categoria, fechaInicial, fechaFinal)
                .switchIfEmpty(Flux.error(new NotFoundException("No categories found")));
    }
}
