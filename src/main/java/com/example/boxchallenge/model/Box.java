package com.example.boxchallenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

@Table(name = "box")
public class Box {
    @Id
    private String txref;
    private double weightLimit;
    private int batteryCapacity;
    @Enumerated(EnumType.STRING)
    private BoxState state;
    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

    public double getTotalItemWeight() {
        if (items == null) {
            return 0.0;
        }
        return items.stream().mapToDouble(Item::getWeight).sum();
    }
}