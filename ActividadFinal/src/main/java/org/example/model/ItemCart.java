package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemCart {
    private String productId;
    private String name;
    private int quantity;
    private double unitPrice;
    private double subtotal;
}