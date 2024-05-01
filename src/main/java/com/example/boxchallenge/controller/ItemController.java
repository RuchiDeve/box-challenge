package com.example.boxchallenge.controller;

import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boxes/{txref}/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<String> addItemToBox(@Valid @PathVariable String txref, @RequestBody ItemDto itemDto) {
        Item item = itemService.addItemToBox(txref, itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to box successfully");
    }

    @GetMapping
    public ResponseEntity<List<Item>> getItemsInBox(@PathVariable String txref) {
        List<Item> items = itemService.getItemsInBox(txref);
        if (items == null || items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(items);
        }
    }
}
