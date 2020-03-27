package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.video.dto.VideoDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReviewDto {

    private String id;
    private String reviewType;

    @JsonProperty("aspects")
    private List<AspectPreferenceDto> aspectPreferenceDtoList;

    @JsonProperty("brandRating")
    private List<BrandRatingDto> brandRatingDtoList;

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
