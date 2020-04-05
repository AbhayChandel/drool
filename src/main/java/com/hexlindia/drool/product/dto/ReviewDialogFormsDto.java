package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ReviewDialogFormsDto {

    private String productId;
    private String brandId;

    @JsonProperty("aspectTemplates")
    private List<AspectTemplateDto> aspectTemplateDtoList;


    private List<String> brandRatingMetrics;
}
