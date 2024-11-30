package com.candelo.mariela.model.carritodecompra;

import com.candelo.mariela.model.item.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarritoDeCompra {

    private UUID id;
    private int clienteId;
    private List<Item> items;
    private double total;

}
