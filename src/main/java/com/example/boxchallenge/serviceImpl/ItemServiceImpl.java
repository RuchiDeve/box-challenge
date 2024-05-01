package com.example.boxchallenge.serviceImpl;

import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.repository.BoxRepository;
import com.example.boxchallenge.repository.ItemRepository;
import com.example.boxchallenge.service.ItemService;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BoxRepository boxRepository;

    public ItemServiceImpl(ItemRepository itemRepository, BoxRepository boxRepository) {
        this.itemRepository = itemRepository;
        this.boxRepository = boxRepository;
    }

    @Override
    public Item addItemToBox(String txref, ItemDto itemDto) {
        Box box = boxRepository.findByTxref(txref)
                .orElseThrow(() -> new IllegalArgumentException("Box not found for txref: " + txref));

        if (box.getState() != BoxState.IDLE && box.getState() != BoxState.LOADING) {
            throw new IllegalArgumentException("Box is not in a state suitable for loading. It should be in IDLE or LOADING state.");
        }

        double additionalWeight = itemDto.getWeight();
        double totalWeight = box.getTotalItemWeight() + additionalWeight;
        if (totalWeight > box.getWeightLimit()) {
            throw new IllegalArgumentException("Adding this item exceeds the box's weight limit.");
        }

        if (box.getBatteryCapacity() < 25) {
            throw new IllegalArgumentException("Box battery capacity is below 25%. Cannot load items.");
        }

        Item item = new Item();
        item.setName(itemDto.getName());
        item.setWeight(itemDto.getWeight());
        item.setCode(itemDto.getCode());
        item.setBox(box);

        box.getItems().add(item);
        itemRepository.save(item);
        boxRepository.save(box);

        return item;
    }


    }


