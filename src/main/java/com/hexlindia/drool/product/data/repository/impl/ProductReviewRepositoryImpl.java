package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.doc.TextReviewDoc;
import com.hexlindia.drool.product.data.repository.api.ProductReviewRepository;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
public class ProductReviewRepositoryImpl implements ProductReviewRepository {

    private static final String PRODUCTS_COLLECTION_NAME = "products";

    private final MongoOperations mongoOperations;

    public ProductReviewRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public TextReviewDoc save(TextReviewDoc textReviewDoc, ObjectId productId) {
        textReviewDoc.setDatePosted(LocalDateTime.now());
        Update update = new Update().addToSet("reviews.textReviews", textReviewDoc).inc("reviews.totalReview", 1).inc("reviews.textReview", 1);
        UpdateResult reviewSaveResult = mongoOperations.updateFirst(new Query(where("id").is(productId)), update, ProductDoc.class);
        if (reviewSaveResult.getModifiedCount() > 0) {
            return textReviewDoc;
        }
        return null;
    }
}
