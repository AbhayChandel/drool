package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.data.doc.ReviewDoc;
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

    private static final String REVIEWS_PATH = "reviews.";

    private static final String TEXT_REVIEWS_ARRAY = "textReviews";

    private static final String VIDEO_REVIEWS_ARRAY = "videoReviews";

    private static final String TEXT_REVIEWS_COUNT = "textReviewsCount";

    private static final String VIDEO_REVIEWS_COUNT = "videoReviewsCount";

    private static final String TOTAL_REVIEWS_COUNT = "totalReviewsCount";


    private final MongoOperations mongoOperations;

    public ProductReviewRepositoryImpl(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Override
    public ReviewDoc save(ReviewDoc reviewDoc, ObjectId productId) {
        reviewDoc.setDatePosted(LocalDateTime.now());
        Update update = new Update().addToSet(getReviewsArray(reviewDoc.getReviewType()), reviewDoc).inc(REVIEWS_PATH + TOTAL_REVIEWS_COUNT, 1).inc(getReviewsCount(reviewDoc.getReviewType()), 1);
        UpdateResult reviewSaveResult = mongoOperations.updateFirst(new Query(where("id").is(productId)), update, ProductDoc.class);
        if (reviewSaveResult.getModifiedCount() > 0) {
            return reviewDoc;
        }
        return null;
    }

    private String getReviewsArray(ReviewType reviewType) {
        return REVIEWS_PATH + (reviewType.equals(ReviewType.text) ? TEXT_REVIEWS_ARRAY : VIDEO_REVIEWS_ARRAY);
    }

    private String getReviewsCount(ReviewType reviewType) {
        return REVIEWS_PATH + (reviewType.equals(ReviewType.text) ? TEXT_REVIEWS_COUNT : VIDEO_REVIEWS_COUNT);
    }
}
