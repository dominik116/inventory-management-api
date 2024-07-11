package com.inventory.api.inventory_management.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class ArticleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("EAN")
    private String ean;

    private String name;

    private String description;

    private Integer quantity;

    private Float price;
}
