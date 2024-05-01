package com.example.boxchallenge.controller;

import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.service.BoxService;
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

    @PostMapping
    public ResponseEntity<?> createBox(@Valid @RequestBody BoxDto boxDto) {
        Box createdBox = boxService.createBox(boxDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBox);
    }

    @GetMapping("/{txref}")
    public ResponseEntity<Box> getBox(@PathVariable String txref) {
        Box box = boxService.getBox(txref);
        if (box == null) {
            return ResponseEntity.notFound().build();
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
}
