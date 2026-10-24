package com.elkabani.mydb.controllers;

import com.elkabani.mydb.dtos.ProductDto;
import com.elkabani.mydb.entities.Product;
import com.elkabani.mydb.mappers.ProductMapper;
import com.elkabani.mydb.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RestController
@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(
            @RequestParam(name ="categoryId", required = false) Long categoryId){

            List<Product> products;

            if (categoryId == null) {
                products =  productRepository.findAll();
            } else {
                products = productRepository.findByCategoryId(categoryId);
            }

            List<ProductDto> productDtos = new ArrayList<>();

            for (Product product : products) {
                productDtos.add(productMapper.toDto(product));
            }

            return productDtos;
    }
}


