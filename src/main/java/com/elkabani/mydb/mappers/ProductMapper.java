package com.elkabani.mydb.mappers;

import com.elkabani.mydb.dtos.ProductDto;
import com.elkabani.mydb.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    Product toEntity(ProductDto productDto);
}
