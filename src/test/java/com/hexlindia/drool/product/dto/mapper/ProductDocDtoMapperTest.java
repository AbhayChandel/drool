package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.AspectOption;
import com.hexlindia.drool.product.data.doc.AspectResultDoc;
import com.hexlindia.drool.product.data.doc.AspectsDoc;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.ProductDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductDocDtoMapperTest {

    @Autowired
    ProductDocDtoMapper productDocDtoMapper;

    @Test
    void toDto() {
        AspectResultDoc aspectStyle = new AspectResultDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Bohemian", 5)));
        AspectResultDoc aspectOccasion = new AspectResultDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 45)));
        ProductDoc productDoc = new ProductDoc();
        AspectsDoc aspectsDoc = new AspectsDoc();
        aspectsDoc.setAspectResultDocList(Arrays.asList(aspectStyle, aspectOccasion));
        aspectsDoc.setExternalAspectIds(Arrays.asList(new ObjectId(), new ObjectId(), new ObjectId()));
        productDoc.setAspectsDoc(aspectsDoc);
        productDoc.setName("Lakme 9 to 5");
        productDoc.setActive(true);
        ProductDto productDto = productDocDtoMapper.toDto(productDoc);
        assertNotNull(productDto);
        assertEquals("Lakme 9 to 5", productDto.getName());
        assertEquals("1", productDto.getAspectResults().get(0).getId());
        assertEquals("pc", productDto.getAspectResults().get(0).getDisplayComponent());
        assertEquals("Top Styles", productDto.getAspectResults().get(0).getTitle());
        assertEquals(45, productDto.getAspectResults().get(0).getVotes());
        assertEquals("Bohemian", productDto.getAspectResults().get(0).getOptions().get(0).getName());
        assertEquals(5, productDto.getAspectResults().get(0).getOptions().get(0).getVotes());
        assertEquals("2", productDto.getAspectResults().get(1).getId());
        assertEquals("pc", productDto.getAspectResults().get(1).getDisplayComponent());
        assertEquals("Occasion", productDto.getAspectResults().get(1).getTitle());
        assertEquals(35, productDto.getAspectResults().get(1).getVotes());
        assertEquals("Wedding", productDto.getAspectResults().get(1).getOptions().get(0).getName());
        assertEquals(45, productDto.getAspectResults().get(1).getOptions().get(0).getVotes());

    }
}