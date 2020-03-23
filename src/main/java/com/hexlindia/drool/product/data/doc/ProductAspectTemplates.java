package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class ProductAspectTemplates {

    private ObjectId id;

    @Field(value = "allaspects")
    private List<AspectTemplate> aspectTemplates;
}
