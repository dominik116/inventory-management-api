package com.inventory.api.inventory_management.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class PagingDto<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private List<T> content;

    private Integer page;

    private Integer size;

    private String sort;

    private Integer total;
}
