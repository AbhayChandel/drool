package com.hexlindia.drool.product.data.doc;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.List;

@Document(collection = "brands")
@NoArgsConstructor
@Getter
@Setter
public class BrandDoc {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private ObjectId id;
    private Boolean active;
    private String name;

    @Field(value = "rating_metrics")
    private List<String> ratingMetrics;
}
