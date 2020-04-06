package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchProductDto {

    private String id;
    private String name;

    @JsonProperty("brand")
    private BrandRefDto brandRefDto;
}
