package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRef {

    private String id;
    private String name;
    private String type;

    public ProductRef(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
