package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.product.data.doc.AspectDoc;
import com.hexlindia.drool.product.data.doc.AspectOption;
import com.hexlindia.drool.product.data.doc.ProductDoc;
import com.hexlindia.drool.product.dto.ProductDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ProductViewDocDtoMapperTest {

    @Autowired
    ProductDocDtoMapper productDocDtoMapper;

    @Test
    void toDto() {
        AspectDoc aspectStyle = new AspectDoc("1", "pc", "Top Styles", 45,
                Arrays.asList(new AspectOption("Bohemian", 5)));
        AspectDoc aspectOccasion = new AspectDoc("2", "pc", "Occasion", 35,
                Arrays.asList(new AspectOption("Wedding", 45)));
        ProductDoc productDoc = new ProductDoc("Lakme 9 to 5", Arrays.asList(aspectStyle, aspectOccasion));
        productDoc.setActive(true);
        ProductDto productDto = productDocDtoMapper.toDto(productDoc);
        assertNotNull(productDto);
        assertEquals("Lakme 9 to 5", productDto.getName());
        assertEquals("1", productDto.getAspects().get(0).getId());
        assertEquals("pc", productDto.getAspects().get(0).getDisplayComponent());
        assertEquals("Top Styles", productDto.getAspects().get(0).getTitle());
        assertEquals(45, productDto.getAspects().get(0).getVotes());
        assertEquals("Bohemian", productDto.getAspects().get(0).getOptions().get(0).getName());
        assertEquals(5, productDto.getAspects().get(0).getOptions().get(0).getVotes());
        assertEquals("2", productDto.getAspects().get(1).getId());
        assertEquals("pc", productDto.getAspects().get(1).getDisplayComponent());
        assertEquals("Occasion", productDto.getAspects().get(1).getTitle());
        assertEquals(35, productDto.getAspects().get(1).getVotes());
        assertEquals("Wedding", productDto.getAspects().get(1).getOptions().get(0).getName());
        assertEquals(45, productDto.getAspects().get(1).getOptions().get(0).getVotes());

    }
}