package com.example.boxchallenge.repository;

import com.example.boxchallenge.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBoxTxref(String txref);

}
