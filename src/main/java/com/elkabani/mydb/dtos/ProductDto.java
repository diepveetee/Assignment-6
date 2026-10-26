package com.elkabani.mydb.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter

public class ProductDto {
    private Long id;
    private String name;
    // private String description;
    private Long categoryId;
    private BigDecimal price;

}
