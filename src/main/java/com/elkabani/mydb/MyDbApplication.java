package com.elkabani.mydb;

import com.elkabani.mydb.entities.Category;
import com.elkabani.mydb.entities.Product;
import com.elkabani.mydb.repositories.CategoryRepository;
import com.elkabani.mydb.repositories.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;



import java.math.BigDecimal;

@SpringBootApplication
public class MyDbApplication {

    public static void main(String[] args) {

        ApplicationContext context =
                SpringApplication.run(MyDbApplication.class, args);

        var categoryRepository =
                context.getBean(CategoryRepository.class);

        var productRepository =
                context.getBean(ProductRepository.class);

        // Create a new category
        var category = new Category();
        category.setName("Electronics");
        category = categoryRepository.save(category);

        var category2 = new Category();
        category2.setName("Misc");
        category2 = categoryRepository.save(category2);

        // Create a product and assign the category
        var camera = new Product();
        camera.setName("Camera");
        camera.setPrice(new BigDecimal("399.99"));
        camera.setCategory(category);

        productRepository.save(camera);

        // B. Create another product for the existing category
        var headphones = new Product();
        headphones.setName("Headphones");
        headphones.setPrice(new BigDecimal("99.99"));
        headphones.setCategory(category);

        headphones = productRepository.save(headphones);

        var sword = new Product();
        sword.setName("Sword");
        sword.setPrice(new BigDecimal("1199.99"));
        sword.setCategory(category2);

        productRepository.save(sword);


        // C. Delete the sword product
        productRepository.delete(sword);

        System.out.println("Operations completed successfully!");
    }

}
