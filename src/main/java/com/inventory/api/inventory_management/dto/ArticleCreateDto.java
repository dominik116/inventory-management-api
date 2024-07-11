package com.inventory.api.inventory_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ArticleCreateDto {

    @JsonProperty("EAN")
    private String ean;

    private String name;

    private String description;

    private Integer quantity;

    private Float price;
}