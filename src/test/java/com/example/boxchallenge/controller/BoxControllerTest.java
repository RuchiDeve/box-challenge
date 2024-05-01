package com.example.boxchallenge.controller;


import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.service.BoxService;
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
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BoxControllerTest {

    @Mock
    private BoxService boxService;

    @InjectMocks
    private BoxController boxController;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boxController).build();
    }

    @Test
    void testCreateBox() throws Exception {
        // Arrange
        BoxDto boxDto = new BoxDto();
        boxDto.setTxref("testTxref");
        boxDto.setWeightLimit(50);
        boxDto.setBatteryCapacity(75);
        boxDto.setState("IDLE");

        Box createdBox = new Box();
        createdBox.setTxref("testTxref");
        createdBox.setWeightLimit(50);
        createdBox.setBatteryCapacity(75);
        createdBox.setState(BoxState.IDLE);

        when(boxService.createBox(any(BoxDto.class))).thenReturn(createdBox);

        mockMvc.perform(post("/api/boxes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(boxDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(createdBox)));

        verify(boxService).createBox(any(BoxDto.class));
    }

}
