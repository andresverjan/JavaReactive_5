package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "administraciones", name = "\"PRODUCTS\"")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Products {
    @Id
    private String id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private int stock;
    private String category;

}
