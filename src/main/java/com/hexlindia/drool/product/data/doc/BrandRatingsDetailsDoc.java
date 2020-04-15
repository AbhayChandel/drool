package com.hexlindia.drool.product.data.doc;

import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "brand_ratings_details")
@Getter
@Setter
public class BrandRatingsDetailsDoc {

    private ObjectId id;
    private ObjectId reviewId;

    @Field(value = "brand_ratings")
    private List<BrandRatingMetricDoc> brandRatingMetricDocList;
    private BrandRef brandRef;
    private UserRef userRef;
}
