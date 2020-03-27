package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductReviewRepositoryTest {

    private final ProductReviewRepository productReviewRepository;
    private final MongoOperations mongoOperations;

    private Map<String, ObjectId> insertedProducts = new HashMap<>();

    @Autowired
    public ProductReviewRepositoryTest(ProductReviewRepository productReviewRepository, MongoOperations mongoOperations) {
        this.productReviewRepository = productReviewRepository;
        this.mongoOperations = mongoOperations;
    }

    @BeforeEach
    void setup() {
        insertProducts();
    }

    @Test
    void saveTextReview() {
        TextReviewDoc textReviewDoc = new TextReviewDoc();
        textReviewDoc.setReviewType("text");
        textReviewDoc.setRecommendation("1");
        textReviewDoc.setDetailedReview("This is a detailed review");
        textReviewDoc.setReviewSummary("This is a review summary");
        assertNotNull(productReviewRepository.save(textReviewDoc, insertedProducts.get("active")));
    }

    /*@Test
    void saveVideoReview() {
        TextReviewDoc textReviewDoc = new TextReviewDoc();
        textReviewDoc.setReviewType("text");
        textReviewDoc.setRecommendation("1");
        textReviewDoc.setVideoId(ObjectId.get());
        assertNotNull(productReviewRepository.save(textReviewDoc, insertedProducts.get("active")));
    }*/

    private void insertProducts() {
        ProductDoc productDocActive = new ProductDoc();
        productDocActive.setName("Lakme 9 to 5");
        productDocActive.setActive(true);
        this.mongoOperations.save(productDocActive);
        insertedProducts.put("active", productDocActive.getId());
        ProductDoc productDocInactive = new ProductDoc();
        productDocInactive.setName("L' Oreal Collosal Kajal");
        productDocInactive.setActive(false);
        this.mongoOperations.save(productDocInactive);
        insertedProducts.put("inactive", productDocInactive.getId());
    }
}