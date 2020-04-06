package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.BrandRef;
import com.hexlindia.drool.product.data.doc.SearchProductRef;
import com.hexlindia.drool.product.dto.SearchProductDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SearchProductMapperTest {

    @Autowired
    SearchProductMapper searchProductMapper;

    @Test
    void toDto() {
        SearchProductRef searchProductRef = new SearchProductRef();
        ObjectId productId = new ObjectId();
        searchProductRef.setId(productId);
        ObjectId brandId = new ObjectId();
        searchProductRef.setBrandRef(new BrandRef(brandId, "Lakme"));
        SearchProductDto searchProductDto = searchProductMapper.toDto(searchProductRef);
        assertEquals(productId.toHexString(), searchProductDto.getId());
        assertEquals(brandId.toHexString(), searchProductDto.getBrandRefDto().getId());
        assertEquals("Lakme", searchProductDto.getBrandRefDto().getName());
    }

    @Test
    void toDtoList() {
        SearchProductRef searchProductRefLakme = new SearchProductRef();
        ObjectId productIdlipcolor = new ObjectId();
        searchProductRefLakme.setId(productIdlipcolor);
        ObjectId brandIdLakme = new ObjectId();
        searchProductRefLakme.setBrandRef(new BrandRef(brandIdLakme, "Lakme"));
        SearchProductRef searchProductRefLoreal = new SearchProductRef();
        ObjectId productIdlipbalm = new ObjectId();
        searchProductRefLoreal.setId(productIdlipbalm);
        ObjectId brandIdLoreal = new ObjectId();
        searchProductRefLoreal.setBrandRef(new BrandRef(brandIdLoreal, "Loreal"));
        List<SearchProductDto> searchProductDtoList = searchProductMapper.toDtoList(Arrays.asList(searchProductRefLakme, searchProductRefLoreal));
        assertEquals(2, searchProductDtoList.size());
        assertEquals(productIdlipcolor.toHexString(), searchProductDtoList.get(0).getId());
        assertEquals(brandIdLakme.toHexString(), searchProductDtoList.get(0).getBrandRefDto().getId());
        assertEquals("Lakme", searchProductDtoList.get(0).getBrandRefDto().getName());
        assertEquals(productIdlipbalm.toHexString(), searchProductDtoList.get(1).getId());
        assertEquals(brandIdLoreal.toHexString(), searchProductDtoList.get(1).getBrandRefDto().getId());
        assertEquals("Loreal", searchProductDtoList.get(1).getBrandRefDto().getName());
    }
}