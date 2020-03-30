package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.BrandCriteriaRatingsDetailsDoc;
import com.hexlindia.drool.product.dto.BrandCriteriaRatingsDetailsDto;
import com.hexlindia.drool.product.dto.BrandCriterionRatingDto;
import com.hexlindia.drool.product.dto.BrandRefDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BrandCriteriaRatingsDetailsMapperTest {

    @Autowired
    BrandCriteriaRatingsDetailsMapper brandCriteriaRatingsDetailsMapper;

    @Test
    void toDoc() {
        BrandCriterionRatingDto brandCriterionRatingDtoTrendy = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrendy.setName("Trendy");
        brandCriterionRatingDtoTrendy.setRating(4);
        BrandCriterionRatingDto brandCriterionRatingDtoTrustable = new BrandCriterionRatingDto();
        brandCriterionRatingDtoTrustable.setName("Trustable");
        brandCriterionRatingDtoTrustable.setRating(2);
        ObjectId brandId = new ObjectId();

        BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto = new BrandCriteriaRatingsDetailsDto(null, Arrays.asList(brandCriterionRatingDtoTrendy, brandCriterionRatingDtoTrustable), new BrandRefDto(brandId.toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        BrandCriteriaRatingsDetailsDoc brandCriteriaRatingsDetailsDoc = brandCriteriaRatingsDetailsMapper.toDoc(brandCriteriaRatingsDetailsDto);
        assertEquals("Trendy", brandCriteriaRatingsDetailsDoc.getBrandCriterionRatingDocList().get(0).getName());
        assertEquals(4, brandCriteriaRatingsDetailsDoc.getBrandCriterionRatingDocList().get(0).getRating());
        assertEquals("Trustable", brandCriteriaRatingsDetailsDoc.getBrandCriterionRatingDocList().get(1).getName());
        assertEquals(2, brandCriteriaRatingsDetailsDoc.getBrandCriterionRatingDocList().get(1).getRating());
        assertEquals("Lakme", brandCriteriaRatingsDetailsDoc.getBrandRef().getName());
        assertEquals(brandId, brandCriteriaRatingsDetailsDoc.getBrandRef().getId());
        assertEquals("u123", brandCriteriaRatingsDetailsDoc.getUserRef().getId());
        assertEquals("username123", brandCriteriaRatingsDetailsDoc.getUserRef().getUsername());
    }
}