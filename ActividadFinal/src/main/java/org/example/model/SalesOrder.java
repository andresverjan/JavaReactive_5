package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.List;

@Table(schema = "administraciones", name = "\"SALES_ORDER\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesOrder {
    @Id
    private String id;
    private String clientId;
    private LocalDate date;
    private List<ItemOrder> items;
    private double total;
}
