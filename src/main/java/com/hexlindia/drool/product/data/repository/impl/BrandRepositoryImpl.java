package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandDoc;
import com.hexlindia.drool.product.data.repository.api.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class BrandRepositoryImpl implements BrandRepository {

    private final MongoOperations mongoOperations;
    private static final String BRAND_COLLECTION_NAME = "brands";


    @Override
    public List<String> getRatingMetrics(ObjectId id) {

        Query query = new Query(where("_id").is(id).andOperator(where("active").is(true)));
        query.fields().include("rating_metrics").exclude("_id");
        return mongoOperations.findOne(query, BrandDoc.class).getRatingMetrics();
    }
}
