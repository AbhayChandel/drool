package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.BrandRating;
import com.hexlindia.drool.product.business.api.usecase.BrandRatingsDetails;
import com.hexlindia.drool.product.data.repository.api.BrandRatingRepository;
import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BrandRatingImpl implements BrandRating {

    private final BrandRatingRepository brandRatingRepository;
    private final BrandRatingsDetails brandRatingsDetails;


    @Override
    public List<String> getRatingMetrics(String id) {
        return brandRatingRepository.getRatingMetrics(new ObjectId(id));
    }

    @Override
    public ObjectId saveBrandRatings(BrandRatingsDetailsDto brandRatingsDetailsDto) {
        ObjectId ratingsDetailsId = brandRatingsDetails.saveRatings(brandRatingsDetailsDto);
        brandRatingRepository.saveRatingSummary(new ObjectId(brandRatingsDetailsDto.getBrandRefDto().getId()), brandRatingsDetailsDto.getBrandRatingMetricDtoList());
        return ratingsDetailsId;
    }
}
