package com.hexlindia.drool.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductAspectTemplatesDto {

    private String id;
    private List<AspectTemplateDto> aspectTemplateDtoList;
}
