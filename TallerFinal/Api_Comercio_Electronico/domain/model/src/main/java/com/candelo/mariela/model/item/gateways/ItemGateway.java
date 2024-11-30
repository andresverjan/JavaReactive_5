package com.candelo.mariela.model.item.gateways;

import com.candelo.mariela.model.item.Item;
import reactor.core.publisher.Mono;

public interface ItemGateway {

    Mono<Item> saveItem(Item item);

    Mono<Void> deleteItemById(String id);

    Mono<Item> getItemById(String id);

    Mono<Item> updateItem(Item item);
}
