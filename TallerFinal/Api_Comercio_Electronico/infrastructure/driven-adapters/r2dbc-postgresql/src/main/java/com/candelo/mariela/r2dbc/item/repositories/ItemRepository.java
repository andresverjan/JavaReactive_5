package com.candelo.mariela.r2dbc.item.repositories;

import com.candelo.mariela.r2dbc.item.entities.ItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface ItemRepository extends ReactiveCrudRepository<ItemEntity, UUID> {
}