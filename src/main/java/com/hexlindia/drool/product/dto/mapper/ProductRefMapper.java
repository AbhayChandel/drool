package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.data.doc.ProductRef;
import com.hexlindia.drool.product.dto.ProductRefDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductRefMapper {

    ProductRef toDoc(ProductRefDto productRefDto);

    ProductRefDto toDto(ProductRef productRef);

    List<ProductRef> toDocList(List<ProductRefDto> productRefDtoList);

    List<ProductRefDto> toDto(List<ProductRef> productRefList);
}
