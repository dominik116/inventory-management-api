package com.inventory.api.inventory_management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "articles")
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ean;

    @Column(nullable = false)
    private String name;

    private String description;

    private Integer quantity;

    private Float price;
}