package com.example.boxchallenge.service;

import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.Item;

import java.util.List;

public interface BoxService {
    Box createBox(BoxDto boxDto);
    Box getBox(String txref);

    List<Box> getAvailableBoxes();
    int getBoxBattery(String txref);
    List<Item> checkLoadedItemsInBox(String boxSerialNumber);

}
