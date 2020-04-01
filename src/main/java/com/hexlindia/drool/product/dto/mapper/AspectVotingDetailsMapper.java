package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AspectVotingMapper.class, ProductRefMapper.class, UserRefMapper.class, ObjectIdMapper.class})
public interface AspectVotingDetailsMapper {

    @Mapping(source = "aspectVotingDtoList", target = "aspectVotingDocList")
    @Mapping(source = "productRefDto", target = "productRef")
    @Mapping(source = "userRefDto", target = "userRef")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = StringToObjectIdMapping.class)
    AspectVotingDetailsDoc toDoc(AspectVotingDetailsDto aspectVotingDetailsDto);
}
