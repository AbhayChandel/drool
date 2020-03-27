package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.dto.TextReviewDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TextReviewMapperTest {

    @Autowired
    TextReviewMapper textReviewMapper;

    @Test
    void toTextReviewDoc() {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewType(ReviewType.text);
        reviewDto.setRecommendation("1");

        TextReviewDto textReviewDto = new TextReviewDto();
        textReviewDto.setDetailedReview("THis is a details text review");
        textReviewDto.setReviewSummary("This is text review summary");
        reviewDto.setTextReviewDto(textReviewDto);

        ReviewDoc reviewDoc = textReviewMapper.toReviewDoc(reviewDto);

        assertEquals(ReviewType.text, reviewDoc.getReviewType());
        assertEquals("1", reviewDoc.getRecommendation());
        assertEquals("THis is a details text review", reviewDoc.getDetailedReview());
        assertEquals("This is text review summary", reviewDoc.getReviewSummary());
    }
}