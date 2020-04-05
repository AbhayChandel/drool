package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.product.data.doc.BrandRatingsDetailsDoc;
import com.hexlindia.drool.product.dto.BrandRatingsDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandCriterionRatingMapper.class, BrandRefMapper.class, UserRefMapper.class, ObjectIdMapper.class})
public interface BrandRatingsDetailsMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    @Mapping(source = "getBrandRatingMetricDtoList", target = "brandCriterionRatingDocList")
    @Mapping(source = "brandRefDto", target = "brandRef")
    @Mapping(source = "userRefDto", target = "userRef")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = StringToObjectIdMapping.class)
    BrandRatingsDetailsDoc toDoc(BrandRatingsDetailsDto brandRatingsDetailsDto);
}
