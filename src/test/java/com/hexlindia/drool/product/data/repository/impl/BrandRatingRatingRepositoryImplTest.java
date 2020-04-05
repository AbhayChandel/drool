package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandDoc;
import com.hexlindia.drool.product.data.repository.api.BrandRatingRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BrandRatingRatingRepositoryImplTest {

    private final BrandRatingRepository brandRatingRepository;
    private final MongoOperations mongoOperations;

    private List<ObjectId> insertedBrands = new ArrayList<>();

    @Autowired
    public BrandRatingRatingRepositoryImplTest(BrandRatingRepository brandRatingRepository, MongoOperations mongoOperations) {
        this.brandRatingRepository = brandRatingRepository;
        this.mongoOperations = mongoOperations;
    }

    @Test
    void test_getRatingMetrics() {
        List<String> ratingMetrics = brandRatingRepository.getRatingMetrics(insertedBrands.get(0));
        assertNotNull(ratingMetrics);
        assertEquals(5, ratingMetrics.size());
    }

    @BeforeEach
    void setup() {
        insertBrands();
    }

    private void insertBrands() {
        List<String> brandRatingMetrics = new ArrayList<>();
        brandRatingMetrics.add("Trustable");
        brandRatingMetrics.add("Affordable");
        brandRatingMetrics.add("Trendy");
        brandRatingMetrics.add("Quality");
        brandRatingMetrics.add("Overall");
        BrandDoc brandDoc = new BrandDoc();
        brandDoc.setRatingMetrics(brandRatingMetrics);
        brandDoc.setName("Maybelline");
        brandDoc.setActive(true);
        mongoOperations.save(brandDoc);
        insertedBrands.add(brandDoc.getId());
    }


}