package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.AspectTemplate;
import com.hexlindia.drool.product.dto.AspectTemplateDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AspectTemplateMapperImplTest {

    @Autowired
    AspectTemplateMapper aspectTemplateMapper;

    @Test
    void templateDto() {
        AspectTemplate aspectTemplate = new AspectTemplate();
        ObjectId objectId = ObjectId.get();
        aspectTemplate.setId(objectId);
        aspectTemplate.setTitle("This is first aspect");
        aspectTemplate.setOptions(Arrays.asList("First", "Second", "Third"));
        AspectTemplateDto aspectTemplateDto = aspectTemplateMapper.templateDto(aspectTemplate);
        assertEquals(objectId.toHexString(), aspectTemplateDto.getId());
        assertEquals("This is first aspect", aspectTemplateDto.getTitle());
        assertEquals(3, aspectTemplateDto.getOptions().size());
    }
}