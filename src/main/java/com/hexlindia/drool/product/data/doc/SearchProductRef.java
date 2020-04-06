package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Getter
@Setter
public class SearchProductRef {

    private ObjectId id;
    private String name;

    @Field(value = "brand")
    private BrandRef brandRef;
}
