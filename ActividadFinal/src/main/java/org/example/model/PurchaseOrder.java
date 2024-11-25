package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(schema = "administraciones", name = "\"PURCHASE_ORDER\"")
public class PurchaseOrder {
    @Id
    private String id;
    private String proveedorId;
    private LocalDate date;
    private List<ItemOrder> items;
    private double total;
}
