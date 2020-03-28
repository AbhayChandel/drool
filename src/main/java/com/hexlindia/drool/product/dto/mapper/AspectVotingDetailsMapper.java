package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AspectVotingMapper.class, ProductRefMapper.class, UserRefMapper.class})
public interface AspectVotingDetailsMapper {

    @Mapping(source = "aspectVotingDtoList", target = "aspectVotingDocList")
    @Mapping(source = "productRefDto", target = "productRef")
    @Mapping(source = "userRefDto", target = "userRef")
    AspectVotingDetailsDoc toDoc(AspectVotingDetailsDto aspectVotingDetailsDto);
}
