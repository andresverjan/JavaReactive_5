package org.example.service;

import org.example.model.ItemOrder;
import org.example.model.PurchaseOrder;
import org.example.repository.ProductRepository;
import org.example.repository.ProveedorRepository;
import org.example.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductRepository productRepository;
    private final ProveedorRepository proveedorRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository,
                                ProductRepository productRepository,
                                ProveedorRepository proveedorRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.productRepository = productRepository;
        this.proveedorRepository = proveedorRepository;
    }

    public Mono<PurchaseOrder> registerOrder(PurchaseOrder order) {
        return proveedorRepository.findById(order.getProveedorId())
                .switchIfEmpty(Mono.error(new RuntimeException("Proveedor no encontrado")))
                .thenMany(Flux.fromIterable(order.getItems()))
                .flatMap(item -> productRepository.findById(item.getProductId())
                        .flatMap(product -> {
                            product.setStock(product.getStock() + item.getQuantity());
                            item.setNameProduct(product.getName());
                            item.setUnitPrice(product.getPrice());
                            item.setSubtotal(item.getQuantity() * product.getPrice());
                            return productRepository.save(product);
                        }))
                .collectList()
                .doOnNext(items -> order.setItems(items))
                .doOnNext(o -> o.setTotal(o.getItems().stream().mapToDouble(ItemOrder::getSubtotal).sum()))
                .flatMap(purchaseOrderRepository::save);
    }

    public Flux<PurchaseOrder> listOrder(LocalDate dateInitial, LocalDate dateEnd) {
        return purchaseOrderRepository.findByDateBetween(dateInitial, dateEnd);
    }

    public Flux<PurchaseOrder> listOrdersByProveedor(String proveedorId, LocalDate dateInitial, LocalDate dateEnd) {
        return purchaseOrderRepository.findByProveedorIdAndDateBetween(proveedorId, dateInitial, dateEnd);
    }
}
