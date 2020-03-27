package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import com.hexlindia.drool.product.dto.ReviewDto;
import org.bson.types.ObjectId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class TextReviewMapper {

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "textReviewDto.detailedReview", target = "detailedReview")
    @Mapping(source = "textReviewDto.reviewSummary", target = "reviewSummary")
    public abstract TextReviewDoc toReviewDoc(ReviewDto reviewDto);

    @AfterMapping
    protected void afterDtoToDocConversion(ReviewDto reviewDto, @MappingTarget TextReviewDoc textReviewDoc) {
        if (reviewDto.getId() != null) {
            textReviewDoc.setId(new ObjectId(reviewDto.getId()));
        }
    }

}
