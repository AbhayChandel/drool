package com.hexlindia.drool.activity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.dto.ProductRefDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FeedDto {

    private String postId;
    private String postType;
    private String postMedium;
    private String title;
    private String sourceId;
    private String datePosted;
    private String likes;
    private String views;
    private String comments;

    @JsonProperty("product")
    private List<ProductRefDto> productRefDtoList;

    @JsonProperty("user")
    private UserRefDto userRefDto;

}
