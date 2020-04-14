package com.hexlindia.drool.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRefDto {

    private String id;
    private String name;
    private String type;

    public ProductRefDto(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
