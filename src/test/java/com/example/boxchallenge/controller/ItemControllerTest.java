package com.example.boxchallenge.controller;


import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private BoxController boxController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();
    }

    @Test
    void testAddItemToBox() throws Exception {
        // Arrange
        String txref = "testTxref";
        ItemDto itemDto = new ItemDto();
        itemDto.setName("Test Item");
        itemDto.setWeight(10);
        itemDto.setCode("ITEM123");

        Item item = new Item();
        item.setName("Test Item");
        item.setWeight(10);
        item.setCode("ITEM123");

        // Mock the ItemService behavior
        when(itemService.addItemToBox(any(String.class), any(ItemDto.class))).thenReturn(item);

        // Act & Assert
        mockMvc.perform(post("/api/boxes/{txref}/items", txref)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemDto)))
                .andExpect(status().isCreated()) // HTTP status should be 201 (CREATED)
                .andExpect(content().string("Item added to box successfully")); // Verify response body

        // Verify that the ItemService's addItemToBox method was called
        verify(itemService).addItemToBox(txref, itemDto);
    }
}
