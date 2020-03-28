package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.product.data.doc.BrandCriteriaRatingsDetailsDoc;
import com.hexlindia.drool.product.data.doc.BrandCriterionRatingDoc;
import com.hexlindia.drool.product.data.doc.BrandRef;
import com.hexlindia.drool.product.data.repository.api.BrandEvaluationRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BrandEvaluationRepositoryImplTest {

    @Autowired
    private BrandEvaluationRepository brandEvaluationRepository;

    @Test
    void saveCriteriaRatings() {
        BrandCriteriaRatingsDetailsDoc brandCriteriaRatingsDetailsDoc = new BrandCriteriaRatingsDetailsDoc();
        BrandCriterionRatingDoc brandCriterionRatingDtoTrendy = new BrandCriterionRatingDoc();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandCriterionRatingDoc brandCriterionRatingDtoTrustable = new BrandCriterionRatingDoc();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        brandCriteriaRatingsDetailsDoc.setBrandCriterionRatingDocList(Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable));
        brandCriteriaRatingsDetailsDoc.setBrandRef(new BrandRef(ObjectId.get(), "Lakme"));
        brandCriteriaRatingsDetailsDoc.setUserRef(new UserRef("u123", "Username123"));
        assertNotNull(brandEvaluationRepository.saveCriteriaRatings(brandCriteriaRatingsDetailsDoc));

    }
}