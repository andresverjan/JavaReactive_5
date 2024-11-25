package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table(schema = "administraciones", name = "\"SHOPPING_CART\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cart {
    @Id
    private String id;
    private String clientId;
    private List<ItemCart> items;
    private double total;
}
