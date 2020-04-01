package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.UserRefDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class BrandCriteriaRatingsDetailsDto {

    private String id;

    private String reviewId;

    @JsonProperty("brandCriteriaRatings")
    private List<BrandCriterionRatingDto> brandCriterionRatingDtoList;

    @JsonProperty("brandRef")
    private BrandRefDto brandRefDto;

    private UserRefDto userRefDto;
}
