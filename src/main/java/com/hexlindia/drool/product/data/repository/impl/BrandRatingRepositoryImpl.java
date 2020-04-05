package com.hexlindia.drool.product.data.repository.impl;

import com.hexlindia.drool.product.data.doc.BrandDoc;
import com.hexlindia.drool.product.data.repository.api.BrandRatingRepository;
import com.hexlindia.drool.product.dto.BrandRatingMetricDto;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class BrandRatingRepositoryImpl implements BrandRatingRepository {

    private final MongoOperations mongoOperations;
    private static final String BRAND_COLLECTION_NAME = "brands";


    @Override
    public List<String> getRatingMetrics(ObjectId id) {

        Query query = new Query(where("_id").is(id).andOperator(where("active").is(true)));
        query.fields().include("rating_metrics").exclude("_id");
        return mongoOperations.findOne(query, BrandDoc.class).getRatingMetrics();
    }

    @Override
    public boolean saveRatingSummary(ObjectId brandId, List<BrandRatingMetricDto> brandRatingMetricDtoList) {
        Update update = new Update();
        for (BrandRatingMetricDto brandRatingMetric : brandRatingMetricDtoList) {
            update = update.inc("ratings_results." + brandRatingMetric.getName(), brandRatingMetric.getRating());
        }
        update = update.inc("ratings_results.total_votes", 1);
        UpdateResult result = mongoOperations.updateFirst(new Query(where("id").is(brandId)), update, BrandDoc.class);
        return result.getModifiedCount() > 0;
    }
}
