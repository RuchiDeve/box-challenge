package com.example.boxchallenge.controller;

import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.service.BoxService;
import com.example.boxchallenge.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boxes")
public class BoxController {

    private final BoxService boxService;
    private final ItemService itemService;

    @PostMapping()
    public ResponseEntity<?> createBox(@Valid @RequestBody BoxDto boxDto) {
        Box createdBox = boxService.createBox(boxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBox);
    }
    @PostMapping("/{txref}/items")
    public ResponseEntity<String> addItemToBox(@Valid @PathVariable String txref, @RequestBody ItemDto itemDto) {
        Item item = itemService.addItemToBox(txref, itemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Item added to box successfully");
    }
    @GetMapping("/{txref}/item")
    public ResponseEntity<Box> getBox(@PathVariable String txref) {
        Box box = boxService.getBox(txref);
        if (box == null) {
            return ResponseEntity.notFound().build();
        }
        List<Item> loadedItems = box.getItems();
        if (loadedItems == null || loadedItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(box);
        }
        return ResponseEntity.ok(box);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Box>> getAvailableBoxes() {
        List<Box> availableBoxes = boxService.getAvailableBoxes();
        return ResponseEntity.ok(availableBoxes);
    }

    @GetMapping("/{txref}/battery")
    public ResponseEntity<Integer> getBoxBattery(@PathVariable String txref) {
        int batteryCapacity = boxService.getBoxBattery(txref);
        return ResponseEntity.ok(batteryCapacity);

    }

    @GetMapping("/{txref}/box/items")
    public ResponseEntity<List<Item>> checkLoadedItemsInBox(@PathVariable String txref) {
        List<Item> loadedItems = boxService.checkLoadedItemsInBox(txref);

        return ResponseEntity.ok(loadedItems);
    }
}
