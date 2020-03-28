package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandCriteriaRatingsDetailsDoc;
import com.hexlindia.drool.product.data.repository.api.BrandEvaluationRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandEvaluationRepositoryImpl implements BrandEvaluationRepository {

    private final MongoOperations mongoOperations;

    @Override
    public ObjectId saveCriteriaRatings(BrandCriteriaRatingsDetailsDoc brandCriteriaRatingsDetailsDoc) {
        brandCriteriaRatingsDetailsDoc = this.mongoOperations.save(brandCriteriaRatingsDetailsDoc);
        return brandCriteriaRatingsDetailsDoc != null ? brandCriteriaRatingsDetailsDoc.getId() : null;
    }
}
