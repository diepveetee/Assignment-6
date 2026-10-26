package com.elkabani.mydb.controllers;

import com.elkabani.mydb.dtos.ProductDto;
import com.elkabani.mydb.entities.Category;
import com.elkabani.mydb.entities.Product;
import com.elkabani.mydb.mappers.ProductMapper;
import com.elkabani.mydb.repositories.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.elkabani.mydb.repositories.CategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Getter
@Setter

public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;


    @PostConstruct
    public void init() {
        System.out.println("***** ProductController loaded *****");
    }

    @GetMapping("/test")
    public String test() {
        return "working";
    }
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

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            ProductDto dto = productMapper.toDto(product.get());
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody ProductDto productDto) {

        System.out.println("DTO categoryId = " + productDto.getCategoryId());
        System.out.println("DTO name = " + productDto.getName());
        System.out.println("DTO price = " + productDto.getPrice());

        Optional<Category> category =
                categoryRepository.findById(productDto.getCategoryId());

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productMapper.toEntity(productDto);
        product.setCategory(category.get());

        Product savedProduct = productRepository.save(product);

        ProductDto savedDto = productMapper.toDto(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDto);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDto productDto) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Category> category =
                categoryRepository.findById(productDto.getCategoryId());

        if (category.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Product product = productOptional.get();

        productMapper.update(productDto, product);

        product.setCategory(category.get());

        Product savedProduct = productRepository.save(product);

        return ResponseEntity.ok(
                productMapper.toDto(savedProduct));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        Optional<Product> product = productRepository.findById(id);

        if (product.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        productRepository.delete(product.get());

        return ResponseEntity.noContent().build();
    }
}


