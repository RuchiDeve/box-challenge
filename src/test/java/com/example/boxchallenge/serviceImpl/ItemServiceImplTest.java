package com.example.boxchallenge.serviceImpl;
import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.repository.BoxRepository;
import com.example.boxchallenge.repository.ItemRepository;
import com.example.boxchallenge.serviceImpl.ItemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    private Box box;
    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        box = new Box();
        box.setTxref("testTxref");
        box.setWeightLimit(50);
        box.setBatteryCapacity(75);
        box.setState(BoxState.IDLE);

        itemDto = new ItemDto();
        itemDto.setName("testItem");
        itemDto.setWeight(10);
        itemDto.setCode("testCode");
    }

    @Test
    void testAddItemToBox_InvalidState() {
        box.setState(BoxState.DELIVERED);
        when(boxRepository.findByTxref("testTxref")).thenReturn(Optional.of(box));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox("testTxref", itemDto);
        });
        assertEquals("Box is not in a state suitable for loading. It should be in IDLE or LOADING state.", exception.getMessage());
    }


    @Test
    void testAddItemToBox_LowBatteryCapacity() {
        box.setBatteryCapacity(20);
        when(boxRepository.findByTxref("testTxref")).thenReturn(Optional.of(box));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox("testTxref", itemDto);
        });
        assertEquals("Box battery capacity is below 25%. Cannot load items.", exception.getMessage());
    }

    @Test
    void testGetItemsInBox() {
        // Arrange
        Item item1 = new Item();
        item1.setWeight(10);
        Item item2 = new Item();
        item2.setWeight(15);

        List<Item> items = Arrays.asList(item1, item2);

        box.setItems(items);

        when(itemRepository.findByBoxTxref("testTxref")).thenReturn(items);


        List<Item> result = itemService.getItemsInBox("testTxref");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(items, result);
    }
}
