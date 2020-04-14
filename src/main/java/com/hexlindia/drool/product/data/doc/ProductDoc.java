package com.hexlindia.drool.product.data.doc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "products")
@NoArgsConstructor
@Getter
@Setter
public class ProductDoc {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private ObjectId id;
    private Boolean active;
    private String name;

    @Field(value = "brand")
    private BrandRef brandRef;

    @Field(value = "aspects")
    private AspectsDoc aspectsDoc;

    @Field(value = "search_tags")
    private List<String> searchTags;
}
