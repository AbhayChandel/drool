package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.BrandRatingsDetailsDoc;
import com.hexlindia.drool.product.dto.BrandRatingMetricDto;
import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import com.hexlindia.drool.product.dto.BrandRefDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BrandRatingCriteriaRatingsDetailsMapperTest {

    @Autowired
    BrandRatingsDetailsMapper brandRatingsDetailsMapper;

    @Test
    void toDoc() {
        BrandRatingMetricDto brandRatingMetricDtoTrendy = new BrandRatingMetricDto();
        brandRatingMetricDtoTrendy.setName("Trendy");
        brandRatingMetricDtoTrendy.setRating(4);
        BrandRatingMetricDto brandRatingMetricDtoTrustable = new BrandRatingMetricDto();
        brandRatingMetricDtoTrustable.setName("Trustable");
        brandRatingMetricDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();
        String reviewId = ObjectId.get().toHexString();
        BrandRatingsDetailsDto brandRatingsDetailsDto = new BrandRatingsDetailsDto(null, reviewId, Arrays.asList(brandRatingMetricDtoTrendy, brandRatingMetricDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        BrandRatingsDetailsDoc brandRatingsDetailsDoc = brandRatingsDetailsMapper.toDoc(brandRatingsDetailsDto);
        assertEquals("Trendy", brandRatingsDetailsDoc.getBrandRatingMetricDocList().get(0).getName());
        assertEquals(4, brandRatingsDetailsDoc.getBrandRatingMetricDocList().get(0).getRating());
        assertEquals("Trustable", brandRatingsDetailsDoc.getBrandRatingMetricDocList().get(1).getName());
        assertEquals(2, brandRatingsDetailsDoc.getBrandRatingMetricDocList().get(1).getRating());
        assertEquals("Lakme", brandRatingsDetailsDoc.getBrandRef().getName());
        assertEquals(brandId, brandRatingsDetailsDoc.getBrandRef().getId());
        assertEquals("u123", brandRatingsDetailsDoc.getUserRef().getId());
        assertEquals("username123", brandRatingsDetailsDoc.getUserRef().getUsername());
        assertEquals(new ObjectId(reviewId), brandRatingsDetailsDoc.getReviewId());
    }
}