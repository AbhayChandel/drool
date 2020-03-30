package com.hexlindia.drool.product.business.api.usecase;

import com.hexlindia.drool.product.dto.BrandCriteriaRatingsDetailsDto;
import org.bson.types.ObjectId;

public interface BrandEvaluation {
    ObjectId saveCriteriaRatings(BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto);
}
