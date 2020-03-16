package com.hexlindia.drool.product.data.doc;

import lombok.AccessLevel;
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
public class ProductDoc {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private String id;
    private Boolean active;
    private String name;
    private List<AspectDoc> aspects;

    public ProductDoc(String name, List<AspectDoc> aspects) {
        this.name = name;
        this.aspects = aspects;
    }
}
