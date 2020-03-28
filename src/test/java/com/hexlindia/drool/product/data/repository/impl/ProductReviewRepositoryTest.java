package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
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
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.text);
        reviewDoc.setRecommendation("1");
        reviewDoc.setDetailedReview("This is a detailed review");
        reviewDoc.setReviewSummary("This is a review summary");
        reviewDoc.setUserRef(new UserRef("u123", "username123"));
        assertNotNull(productReviewRepository.save(reviewDoc, insertedProducts.get("active"), new ArrayList<>()));
    }

    @Test
    void saveVideoReview() {
        ReviewDoc reviewDoc = new ReviewDoc();
        reviewDoc.setReviewType(ReviewType.video);
        reviewDoc.setRecommendation("1");
        reviewDoc.setVideoId(ObjectId.get());
        reviewDoc.setUserRef(new UserRef("u123", "username123"));
        assertNotNull(productReviewRepository.save(reviewDoc, insertedProducts.get("active"), new ArrayList<>()));
    }

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