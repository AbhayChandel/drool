package com.hexlindia.drool.product.data.doc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;

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

    @Field(value = "aspects")
    private AspectsDoc aspectsDoc;
}
