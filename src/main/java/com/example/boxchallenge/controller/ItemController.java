package com.example.boxchallenge.controller;

import com.example.boxchallenge.dto.ItemDto;
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
@RequestMapping("/api/boxes/{txref}/items")
public class ItemController {

    private final ItemService itemService;
    private final BoxService boxService;




}



