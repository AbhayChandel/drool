package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.product.data.doc.BrandCriteriaRatingsDetailsDoc;
import com.hexlindia.drool.product.dto.BrandCriteriaRatingsDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {BrandCriterionRatingMapper.class, BrandRefMapper.class, UserRefMapper.class, ObjectIdMapper.class})
public interface BrandCriteriaRatingsDetailsMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    @Mapping(source = "brandCriterionRatingDtoList", target = "brandCriterionRatingDocList")
    @Mapping(source = "brandRefDto", target = "brandRef")
    @Mapping(source = "userRefDto", target = "userRef")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = StringToObjectIdMapping.class)
    BrandCriteriaRatingsDetailsDoc toDoc(BrandCriteriaRatingsDetailsDto brandCriteriaRatingsDetailsDto);
}
