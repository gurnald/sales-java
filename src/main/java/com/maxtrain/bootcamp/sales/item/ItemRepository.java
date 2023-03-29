package com.maxtrain.bootcamp.sales.item;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository <Item, Integer> {

					Optional<Item> findById(int id);
}
