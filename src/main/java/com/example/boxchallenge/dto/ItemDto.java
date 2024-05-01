package com.example.boxchallenge.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemDto {
        @NotBlank(message = "name cannot be null")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "name can only contain letters, numbers, hyphen, and underscore")
        private String name;

        @NotNull(message = "weight cannot be null")
        private Integer weight;

        @NotNull(message = "code cannot be null")
        @Pattern(regexp = "^[A-Z0-9_]+$", message = "code can only contain uppercase letters, numbers, and underscore")
        private String code;


}
