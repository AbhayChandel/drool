package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.AspectTemplate;
import com.hexlindia.drool.product.data.doc.ProductAspectTemplates;
import com.hexlindia.drool.product.dto.ProductAspectTemplatesDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductAspectTemplatesMapperTest {

    @Autowired
    ProductAspectTemplatesMapper productAspectTemplatesMapper;

    @Test
    void toDto() {
        ProductAspectTemplates productAspectTemplates = new ProductAspectTemplates();
        ObjectId productId = ObjectId.get();
        productAspectTemplates.setId(productId);
        AspectTemplate aspectTemplate1 = new AspectTemplate();
        ObjectId aspectTemplate1Id = ObjectId.get();
        aspectTemplate1.setId(aspectTemplate1Id);
        AspectTemplate aspectTemplate2 = new AspectTemplate();
        ObjectId aspectTemplate2Id = ObjectId.get();
        aspectTemplate2.setId(aspectTemplate2Id);
        productAspectTemplates.setAspectTemplates(Arrays.asList(aspectTemplate1, aspectTemplate2));

        ProductAspectTemplatesDto productAspectTemplatesDto = productAspectTemplatesMapper.toDto(productAspectTemplates);
        assertEquals(productId.toHexString(), productAspectTemplatesDto.getId());
        assertEquals(2, productAspectTemplatesDto.getAspectTemplateDtoList().size());
        assertEquals(aspectTemplate1Id.toHexString(), productAspectTemplatesDto.getAspectTemplateDtoList().get(0).getId());
        assertEquals(aspectTemplate2Id.toHexString(), productAspectTemplatesDto.getAspectTemplateDtoList().get(1).getId());
    }
}