package com.hexlindia.drool.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "products")
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    @Id
    private ObjectId id;
    private Boolean active;
    private String name;
    private List<AspectResultDto> aspectResults;

    public ProductDto(String name, List<AspectResultDto> aspectResults) {
        this.name = name;
        this.aspectResults = aspectResults;
    }
}
