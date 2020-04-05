package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.BrandRatingsDetails;
import com.hexlindia.drool.product.data.repository.api.BrandRatingsDetailsRepository;
import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import com.hexlindia.drool.product.dto.mapper.BrandRatingsDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BrandRatingsDetailsImpl implements BrandRatingsDetails {

    private final BrandRatingsDetailsRepository brandRatingsDetailsRepository;
    private final BrandRatingsDetailsMapper brandRatingsDetailsMapper;

    @Override
    public ObjectId saveRatings(BrandRatingsDetailsDto brandRatingsDetailsDto) {
        return brandRatingsDetailsRepository.saveRatings(brandRatingsDetailsMapper.toDoc(brandRatingsDetailsDto));
    }
}
