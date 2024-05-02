package com.example.boxchallenge.repository;

import com.example.boxchallenge.model.Box;
import com.example.boxchallenge.model.BoxState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoxRepository extends JpaRepository<Box, String> {
    Optional<Box> findByTxref(String txref);

    List<Box> findByState(BoxState state);

    List<Box> findByStateAndBatteryCapacityGreaterThan(BoxState state, int batteryCapacity);

    @Query("SELECT b FROM Box b WHERE b.state = com.example.boxchallenge.model.BoxState.IDLE AND b.batteryCapacity > 25")
    List<Box> findAvailableBoxes(BoxState state);
}
