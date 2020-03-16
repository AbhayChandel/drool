package com.hexlindia.drool.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "products")
@NoArgsConstructor
@Getter
@Setter
public class ProductDto {

    @Id
    private String id;
    private Boolean active;
    private String name;
    private List<AspectDto> aspects;

    public ProductDto(String name, List<AspectDto> aspects) {
        this.name = name;
        this.aspects = aspects;
    }
}
