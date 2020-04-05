package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.BrandRatingMetricDoc;
import com.hexlindia.drool.product.dto.BrandRatingMetricDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandCriterionRatingMapper {

    BrandRatingMetricDoc toDoc(BrandRatingMetricDto brandRatingMetricDto);

    List<BrandRatingMetricDoc> toList(List<BrandRatingMetricDto> brandRatingMetricDtoList);

}
