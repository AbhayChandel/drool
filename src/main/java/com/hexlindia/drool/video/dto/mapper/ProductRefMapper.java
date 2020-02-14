package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.video.data.doc.ProductRef;
import com.hexlindia.drool.video.dto.ProductRefDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRefMapper {

    ProductRef toDoc(ProductRefDto productRefDto);

    ProductRefDto toDto(ProductRef productRef);
}
