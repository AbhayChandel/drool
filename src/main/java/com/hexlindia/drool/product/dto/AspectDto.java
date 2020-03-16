package com.hexlindia.drool.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.product.data.doc.AspectOption;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AspectDto {
    private String id;

    @JsonProperty("displayComp")
    private String displayComponent;
    private String title;
    private Integer votes;
    private List<AspectOption> options;


}
