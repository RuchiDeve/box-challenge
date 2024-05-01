package com.example.boxchallenge.serviceImpl;
import com.example.boxchallenge.dto.BoxDto;
import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import com.example.boxchallenge.repository.BoxRepository;
import com.example.boxchallenge.serviceImpl.BoxServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoxServiceImplTest {

    @Mock
    private BoxRepository boxRepository;

    @InjectMocks
    private BoxServiceImpl boxService;

    @Test
    void testCreateBox() {
        BoxDto boxDto = new BoxDto();
        boxDto.setTxref("testTxref");
        boxDto.setWeightLimit(50);
        boxDto.setBatteryCapacity(75);
        boxDto.setState("IDLE");

        Box box = new Box();
        box.setTxref("testTxref");
        box.setWeightLimit(50);
        box.setBatteryCapacity(75);
        box.setState(BoxState.IDLE);

        when(boxRepository.save(any(Box.class))).thenReturn(box);

        Box createdBox = boxService.createBox(boxDto);

        assertNotNull(createdBox);
        assertEquals(box.getTxref(), createdBox.getTxref());
        assertEquals(box.getWeightLimit(), createdBox.getWeightLimit());
        assertEquals(box.getBatteryCapacity(), createdBox.getBatteryCapacity());
        assertEquals(box.getState(), createdBox.getState());

        verify(boxRepository, times(1)).save(any(Box.class));
    }

    @Test
    void testGetBox() {
        // Arrange
        String txref = "testTxref";
        Box box = new Box();
        box.setTxref(txref);
        box.setWeightLimit(50);
        box.setBatteryCapacity(75);
        box.setState(BoxState.IDLE);

        when(boxRepository.findByTxref(txref)).thenReturn(Optional.of(box));
        Box retrievedBox = boxService.getBox(txref);
        assertNotNull(retrievedBox);
        assertEquals(box.getTxref(), retrievedBox.getTxref());
        assertEquals(box.getWeightLimit(), retrievedBox.getWeightLimit());
        assertEquals(box.getBatteryCapacity(), retrievedBox.getBatteryCapacity());
        assertEquals(box.getState(), retrievedBox.getState());

        verify(boxRepository, times(1)).findByTxref(txref);
    }

    @Test
    void testGetBoxNotFound() {
        String txref = "testTxref";
        when(boxRepository.findByTxref(txref)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> boxService.getBox(txref));
        assertEquals("Box not found for txref: " + txref, exception.getMessage());

        verify(boxRepository, times(1)).findByTxref(txref);
    }

    @Test
    void testGetAvailableBoxes() {
        Box box1 = new Box();
        box1.setTxref("txref1");
        box1.setWeightLimit(50);
        box1.setBatteryCapacity(75);
        box1.setState(BoxState.IDLE);

        Box box2 = new Box();
        box2.setTxref("txref2");
        box2.setWeightLimit(50);
        box2.setBatteryCapacity(80);
        box2.setState(BoxState.IDLE);

        List<Box> availableBoxes = List.of(box1, box2);

        when(boxRepository.findByStateAndBatteryCapacityGreaterThan(BoxState.IDLE, 25)).thenReturn(availableBoxes);


        List<Box> retrievedBoxes = boxService.getAvailableBoxes();

        assertNotNull(retrievedBoxes);
        assertEquals(2, retrievedBoxes.size());
        assertTrue(retrievedBoxes.contains(box1));
        assertTrue(retrievedBoxes.contains(box2));

        verify(boxRepository, times(1)).findByStateAndBatteryCapacityGreaterThan(BoxState.IDLE, 25);
    }

    @Test
    void testGetBoxBattery() {
        // Arrange
        String txref = "testTxref";
        Box box = new Box();
        box.setTxref(txref);
        box.setWeightLimit(50);
        box.setBatteryCapacity(75);
        box.setState(BoxState.IDLE);

        when(boxRepository.findByTxref(txref)).thenReturn(Optional.of(box));

        int batteryCapacity = boxService.getBoxBattery(txref);

        assertEquals(75, batteryCapacity);

        verify(boxRepository, times(1)).findByTxref(txref);
    }
}
