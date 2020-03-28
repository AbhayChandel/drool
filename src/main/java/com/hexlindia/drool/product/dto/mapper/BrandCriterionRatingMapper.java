package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.BrandCriterionRatingDoc;
import com.hexlindia.drool.product.dto.BrandCriterionRatingDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BrandCriterionRatingMapper {

    BrandCriterionRatingDoc toDoc(BrandCriterionRatingDto brandCriterionRatingDto);

    List<BrandCriterionRatingDoc> toList(List<BrandCriterionRatingDto> brandCriterionRatingDtoList);

}
