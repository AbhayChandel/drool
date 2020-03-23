package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.repository.api.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoOperations mongoOperations;
    private static final String PRODUCT_COLLECTION_NAME = "products";

    @Autowired
    public ProductRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public ProductDoc findById(ObjectId id) {
        return mongoOperations.findOne(query(where("id").is(id).andOperator(where("active").is(true))), ProductDoc.class);
    }

    @Override
    public ProductAspectTemplates getAspectTemplates(ObjectId id) {

        MatchOperation matchProduct = match(new Criteria("_id").is(id));
        LookupOperation lookupExternalAspectTemplates = LookupOperation.newLookup().
                from("aspect_templates")
                .localField("aspects.external_aspects")
                .foreignField("_id")
                .as("easpects");

        ProjectionOperation project = Aggregation.project()
                .and("easpects").concatArrays("aspects.internal_aspects").as("allaspects");
        //ReplaceRootOperation replaceRootOperation = Aggregation.replaceRoot("allaspects");
        UnwindOperation unwind = Aggregation.unwind("allaspects");
        ReplaceRootOperation replaceRoot = Aggregation.replaceRoot("allaspects");


        AggregationResults<ProductAspectTemplates> aspectTemplates = this.mongoOperations.aggregate(Aggregation.newAggregation(
                matchProduct,
                lookupExternalAspectTemplates, project
        ), PRODUCT_COLLECTION_NAME, ProductAspectTemplates.class);

        return aspectTemplates.getUniqueMappedResult();
    }
}
