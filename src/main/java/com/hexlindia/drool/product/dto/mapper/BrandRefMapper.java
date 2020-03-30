package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.product.data.doc.BrandRef;
import com.hexlindia.drool.product.dto.BrandRefDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ObjectIdMapper.class})
public interface BrandRefMapper {

    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    BrandRef toDoc(BrandRefDto brandRefDto);

    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    BrandRefDto toDto(BrandRef brandRef);

    List<BrandRef> toDocList(List<BrandRefDto> brandRefDtoList);

    List<BrandRefDto> toDto(List<BrandRef> brandRefList);


}
