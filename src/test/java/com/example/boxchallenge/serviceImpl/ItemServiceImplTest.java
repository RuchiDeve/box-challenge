package com.example.boxchallenge.serviceImpl;

import com.example.boxchallenge.dto.ItemDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.model.Item;
import com.example.boxchallenge.repository.BoxRepository;
import com.example.boxchallenge.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ItemServiceImplTest {

    @Mock
    private BoxRepository boxRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddItemToBoxBoxNotFound() {
        String txref = "box123";
        ItemDto itemDto = new ItemDto("item1", 10, "code123");

        when(boxRepository.findByTxref(txref)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox(txref, itemDto);
        });
    }

    @Test
    void testAddItemToBoxStateNotSuitable() {
        String txref = "box123";
        ItemDto itemDto = new ItemDto("item1", 10, "code123");
        Box box = new Box();
        box.setTxref(txref);
        box.setState(BoxState.IDLE);

        when(boxRepository.findByTxref(txref)).thenReturn(Optional.of(box));
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox(txref, itemDto);
        });
    }

    @Test
    void testAddItemToBoxWeightLimitExceeded() {
        String txref = "box123";
        ItemDto itemDto = new ItemDto("item1", 10, "code123");
        Box box = new Box();
        box.setTxref(txref);
        box.setState(BoxState.IDLE);
        box.setWeightLimit(5.0);
        box.setBatteryCapacity(100);
        when(boxRepository.findByTxref(txref)).thenReturn(Optional.of(box));
        assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox(txref, itemDto);
        });
    }

    @Test
    void testAddItemToBoxBatteryCapacityTooLow() {
        String txref = "box123";
        ItemDto itemDto = new ItemDto("item1", 10, "code123");
        Box box = new Box();
        box.setTxref(txref);
        box.setState(BoxState.IDLE);
        box.setWeightLimit(50.0);
        box.setBatteryCapacity(20);  // Battery capacity too low

        when(boxRepository.findByTxref(txref)).thenReturn(Optional.of(box));

        assertThrows(IllegalArgumentException.class, () -> {
            itemService.addItemToBox(txref, itemDto);
        });
    }
}
