package com.TheSoulStudio.printing_backend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Double price;

    @NotNull
    private Long categoryId;

    private String imageUrl;
}
