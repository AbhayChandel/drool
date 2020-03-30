package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.AspectVotingDoc;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AspectVotingMapper {

    AspectVotingDoc toDoc(AspectVotingDto aspectVotingDto);

    List<AspectVotingDoc> toList(List<AspectVotingDto> aspectVotingDtoList);
}
