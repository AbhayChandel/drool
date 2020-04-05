package com.hexlindia.drool.product.data.repository.api;

import com.hexlindia.drool.product.dto.BrandRatingMetricDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface BrandRatingRepository {

    List<String> getRatingMetrics(ObjectId id);

    boolean saveRatingSummary(ObjectId brandId, List<BrandRatingMetricDto> brandRatingMetricDtoList);
}
