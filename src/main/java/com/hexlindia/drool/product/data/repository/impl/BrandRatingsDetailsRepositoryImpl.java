package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandRatingsDetailsDoc;
import com.hexlindia.drool.product.data.repository.api.BrandRatingsDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BrandRatingsDetailsRepositoryImpl implements BrandRatingsDetailsRepository {

    private final MongoOperations mongoOperations;

    @Override
    public ObjectId saveRatings(BrandRatingsDetailsDoc brandRatingsDetailsDoc) {
        brandRatingsDetailsDoc = this.mongoOperations.save(brandRatingsDetailsDoc);
        return brandRatingsDetailsDoc.getId();
    }
}
