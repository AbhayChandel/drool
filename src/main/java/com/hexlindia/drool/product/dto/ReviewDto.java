package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.video.dto.VideoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private String id;
    private ReviewType reviewType;

    @JsonProperty("aspects")
    private List<AspectVotingDto> aspectVotingDtoList;

    @JsonProperty("brandCriteriaRatingsDetails")
    private BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto;

    private String recommendation;

    @JsonProperty("product")
    private ProductRefDto productRefDto;

    @JsonProperty("textReview")
    private TextReviewDto textReviewDto;

    @JsonProperty("videoReview")
    private VideoDto videoDto;

    @JsonProperty("user")
    private UserRefDto userRefDto;

}
