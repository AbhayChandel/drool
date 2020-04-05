package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import org.bson.types.ObjectId;

public interface BrandRatingsDetails {
    ObjectId saveRatings(BrandRatingsDetailsDto brandRatingsDetailsDto);
}
