package com.candelo.mariela.model.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Item {

    private UUID id;
    private UUID carritoId;
    private UUID productId;
    private String name;
    private int cantidad;
    private double precioUnitario;
    private double total;
}
