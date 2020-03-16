package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.AspectDoc;
import com.hexlindia.drool.product.data.doc.AspectOption;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class ProductViewRepositoryImplTest {

    private final ProductRepository productRepository;
    private final MongoOperations mongoOperations;

    private Map<String, String> insertedProducts = new HashMap<>();

    @Autowired
    public ProductViewRepositoryImplTest(ProductRepository productRepository, MongoOperations mongoOperations) {
        this.productRepository = productRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        AspectDoc aspectStyle = new AspectDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Retro", 25), new AspectOption("Chic", 45),
                        new AspectOption("Bohemian", 5)));
        AspectDoc aspectOccasion = new AspectDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 5), new AspectOption("Clubbing", 35)));
        ProductDoc productDocActive = new ProductDoc("Lakme 9 to 5", Arrays.asList(aspectStyle, aspectOccasion));
        productDocActive.setActive(true);
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc("Lakme 9 to 5", Arrays.asList(aspectStyle, aspectOccasion));
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }

    @Test
    public void test_findByIdAndIsActive() {
        assertNotNull(productRepository.findById(insertedProducts.get("active")));
    }

    @Test
    public void test_findByIdAndIsNotActive() {
        assertNull(productRepository.findById(insertedProducts.get("inactive")));
    }
}