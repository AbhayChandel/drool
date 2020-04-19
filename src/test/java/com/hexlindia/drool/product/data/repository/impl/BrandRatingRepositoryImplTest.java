package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandDoc;
import com.hexlindia.drool.product.data.repository.api.BrandRatingRepository;
import com.hexlindia.drool.product.dto.BrandRatingMetricDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRatingRepositoryImplTest {

    private final BrandRatingRepository brandRatingRepository;
    private final MongoOperations mongoOperations;

    private List<ObjectId> insertedBrands = new ArrayList<>();

    @Autowired
    public BrandRatingRepositoryImplTest(BrandRatingRepository brandRatingRepository, MongoOperations mongoOperations) {
        this.brandRatingRepository = brandRatingRepository;
        this.mongoOperations = mongoOperations;
    }

    @Test
    void getRatingMetrics_Found() {
        List<String> ratingMetrics = brandRatingRepository.getRatingMetrics(insertedBrands.get(0));
        assertNotNull(ratingMetrics);
        assertEquals(5, ratingMetrics.size());
    }

    @Test
    void getRatingMetrics_NotFound() {
        List<String> ratingMetrics = brandRatingRepository.getRatingMetrics(ObjectId.get());
        assertNull(ratingMetrics);
    }

    @Test
    void saveRatingSummary() {
        BrandRatingMetricDto trustable = new BrandRatingMetricDto("Trustable", 5);
        BrandRatingMetricDto affordable = new BrandRatingMetricDto("Affordable", 4);
        BrandRatingMetricDto trendy = new BrandRatingMetricDto("Trendy", 0);
        BrandRatingMetricDto quality = new BrandRatingMetricDto("Quality", 2);
        BrandRatingMetricDto overall = new BrandRatingMetricDto("Overall", 4);
        assertTrue(brandRatingRepository.saveRatingSummary(insertedBrands.get(0), Arrays.asList(trustable, affordable, trendy, quality, overall)));
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