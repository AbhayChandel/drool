package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AspectPreferenceDto {

    @JsonProperty("id")
    private String aspectId;

    @JsonProperty("selected")
    private List<String> selectedOptions;
}
