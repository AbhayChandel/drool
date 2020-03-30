package com.hexlindia.drool.product.business.impl.usecase;

import com.hexlindia.drool.product.business.api.usecase.BrandEvaluation;
import com.hexlindia.drool.product.data.repository.api.BrandEvaluationRepository;
import com.hexlindia.drool.product.dto.BrandCriteriaRatingsDetailsDto;
import com.hexlindia.drool.product.dto.mapper.BrandCriteriaRatingsDetailsMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BrandEvaluationImpl implements BrandEvaluation {

    private final BrandEvaluationRepository brandEvaluationRepository;
    private final BrandCriteriaRatingsDetailsMapper brandCriteriaRatingsDetailsMapper;

    @Override
    public ObjectId saveCriteriaRatings(BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto) {
        return brandEvaluationRepository.saveCriteriaRatings(brandCriteriaRatingsDetailsMapper.toDoc(brandCriteriaRatingsDetailsDto));
    }
}
