package com.hexlindia.drool.product.data.doc;

import com.hexlindia.drool.common.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "brand_criteria_ratings")
@Getter
@Setter
public class BrandCriteriaRatingsDetailsDoc {

    private ObjectId id;
    private List<BrandCriterionRatingDoc> brandCriterionRatingDocList;
    private BrandRef brandRef;
    private UserRef userRef;
}
