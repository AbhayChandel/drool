package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandRatingMetricDoc;
import com.hexlindia.drool.product.data.doc.BrandRatingsDetailsDoc;
import com.hexlindia.drool.product.data.doc.BrandRef;
import com.hexlindia.drool.product.data.repository.api.BrandRatingsDetailsRepository;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BrandRatingEvaluationRepositoryImplTest {

    @Autowired
    private BrandRatingsDetailsRepository brandRatingsDetailsRepository;

    @Test
    void saveCriteriaRatings() {
        BrandRatingsDetailsDoc brandRatingsDetailsDoc = new BrandRatingsDetailsDoc();
        BrandRatingMetricDoc brandCriterionRatingDtoTrendy = new BrandRatingMetricDoc();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandRatingMetricDoc brandCriterionRatingDtoTrustable = new BrandRatingMetricDoc();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        brandRatingsDetailsDoc.setBrandRatingMetricDocList(Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable));
        brandRatingsDetailsDoc.setBrandRef(new BrandRef(ObjectId.get(), "Lakme"));
        brandRatingsDetailsDoc.setUserRef(new UserRef(ObjectId.get(), "Username123"));
        assertNotNull(brandRatingsDetailsRepository.saveRatings(brandRatingsDetailsDoc));

    }
}