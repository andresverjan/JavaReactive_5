package com.candelo.mariela.r2dbc.item.adapters;

import com.candelo.mariela.model.item.Item;
import com.candelo.mariela.model.item.gateways.ItemGateway;
import com.candelo.mariela.r2dbc.item.mappers.ItemMapper;
import com.candelo.mariela.r2dbc.item.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ItemAdapter implements ItemGateway {

    private final ItemRepository itemRepository;

    @Override
    public Mono<Item> save(Item item) {
        return itemRepository.save(ItemMapper.toEntity(item))
                .map(ItemMapper::toModel);
    }

    @Override
    public Mono<Void> deleteItemById(UUID id) {
        return itemRepository.deleteById(id);
    }

    @Override
    public Mono<Item> getItemById(UUID id) {
        return itemRepository.findById(id)
                .map(ItemMapper::toModel);
    }

    @Override
    public Mono<Item> saveItem(Item item) {
        return null;
    }

    @Override
    public Mono<Void> deleteItemById(String id) {
        return null;
    }

    @Override
    public Mono<Item> getItemById(String id) {
        return null;
    }

    @Override
    public Mono<Item> updateItem(Item item) {
        return itemRepository.save(ItemMapper.toEntity(item))
                .map(ItemMapper::toModel);
    }
}