package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
import com.hexlindia.drool.product.dto.ReviewDto;
import org.bson.types.ObjectId;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserRefMapper.class})
public abstract class ReviewMapper {

    @Mapping(source = "id", target = "id", ignore = true)
    @Mapping(source = "textReviewDto.detailedReview", target = "detailedReview")
    @Mapping(source = "textReviewDto.reviewSummary", target = "reviewSummary")
    @Mapping(source = "userRefDto", target = "userRef")
    public abstract ReviewDoc toReviewDoc(ReviewDto reviewDto);

    @AfterMapping
    protected void afterDtoToDocConversion(ReviewDto reviewDto, @MappingTarget ReviewDoc reviewDoc) {
        if (reviewDto.getId() != null) {
            reviewDoc.setId(new ObjectId(reviewDto.getId()));
        }
    }

}
