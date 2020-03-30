package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.data.doc.BrandCriteriaRatingsDetailsDoc;
import org.bson.types.ObjectId;

public interface BrandEvaluationRepository {

    ObjectId saveCriteriaRatings(BrandCriteriaRatingsDetailsDoc brandCriteriaRatingsDetailsDoc);
}
