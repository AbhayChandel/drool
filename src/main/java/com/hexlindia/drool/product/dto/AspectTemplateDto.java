package com.hexlindia.drool.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AspectTemplateDto {

    private String id;
    private String title;
    private List<String> options;
}
