package com.example.boxchallenge.service;
import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Item;

import java.util.List;

    public interface ItemService {
        Item addItemToBox(String txref, ItemDto itemDto);
    }



