package com.example.boxchallenge.serviceImpl;

import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.model.ItemLoadRequest;
import com.example.boxchallenge.repository.BoxRepository;
import com.example.boxchallenge.repository.ItemRepository;
import com.example.boxchallenge.service.BoxService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoxServiceImpl implements BoxService {
    private final BoxRepository boxRepository;


    public BoxServiceImpl(BoxRepository boxRepository, ItemRepository itemRepository) {
        this.boxRepository = boxRepository;
    }

    @Override
    public Box createBox(BoxDto boxDto) {
        Box box = new Box();
        box.setTxref(boxDto.getTxref());
        box.setWeightLimit(boxDto.getWeightLimit());
        box.setBatteryCapacity(boxDto.getBatteryCapacity());

        BoxState state = BoxState.valueOf(boxDto.getState());
        box.setState(state);

        return boxRepository.save(box);
    }

    @Override
    public Box getBox(String txref) {
        Optional<Box> optionalBox = boxRepository.findByTxref(txref);
        return optionalBox.orElseThrow(() -> new IllegalArgumentException("Box not found for txref: " + txref));
    }

    @Override
    public List<Box> getAvailableBoxes() {
        return boxRepository.findByStateAndBatteryCapacityGreaterThan(BoxState.IDLE, 25);
    }

    @Override
    public int getBoxBattery(String txref) {
        Box box = getBox(txref);
        return box.getBatteryCapacity();
    }

    @Override
    public List<Item> checkLoadedItemsInBox(String txref) {
        Box box = boxRepository.findByTxref(txref).orElseThrow(() -> new IllegalArgumentException("Box not found for txref: " + txref));

        return box.getItems();
    }
}