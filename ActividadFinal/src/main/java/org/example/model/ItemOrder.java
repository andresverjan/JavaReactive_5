package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemOrder {
    private String productId;
    private String nameProduct;
    private int quantity;
    private double unitPrice;
    private double subtotal;
}
