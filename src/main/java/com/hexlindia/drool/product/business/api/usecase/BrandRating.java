package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import org.bson.types.ObjectId;

import java.util.List;

public interface BrandRating {

    List<String> getRatingMetrics(String id);

    ObjectId saveBrandRatings(BrandRatingsDetailsDto brandRatingsDetailsDto);
}
