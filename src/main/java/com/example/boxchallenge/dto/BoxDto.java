package com.example.boxchallenge.dto;

import com.example.boxchallenge.model.Item;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)

public class BoxDto {

    @NotBlank(message = "txref cannot be null")
    @Size(max = 20, message = "txref must be less than or equal to 20 characters")
    private String txref;

    @NotNull(message = "weightLimit cannot be null")
    @Min(value = 0, message = "weightLimit must be greater than or equal to 0")
    @Max(value = 500, message = "weightLimit must be less than or equal to 500")
    private int weightLimit;

    @NotNull(message = "batteryCapacity cannot be null")
    @Min(value = 0, message = "batteryCapacity must be greater than or equal to 0")
    @Max(value = 100, message = "batteryCapacity must be less than or equal to 100")
    private Integer batteryCapacity;

    @NotNull(message = "state cannot be null")
    @Pattern(regexp = "IDLE|LOADING|LOADED|DELIVERING|DELIVERED|RETURNING",
            message = "state must be one of the following: IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING")
    private String state;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;

}
