package com.hexlindia.drool.product.dto.mapper;

import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.data.doc.AspectVotingDetailsDoc;
import com.hexlindia.drool.product.dto.AspectVotingDetailsDto;
import com.hexlindia.drool.product.dto.AspectVotingDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AspectVotingDetailsMapperTest {

    @Autowired
    AspectVotingDetailsMapper aspectVotingDetailsMapper;

    @Test
    void toDoc() {
        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("1");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Chic"));
        AspectVotingDto aspectVotingDotOccasion = new AspectVotingDto();
        aspectVotingDotOccasion.setAspectId("2");
        aspectVotingDotOccasion.setSelectedOptions(Arrays.asList("Wedding", "Party"));
        AspectVotingDetailsDto aspectVotingDetailsDto = new AspectVotingDetailsDto(Arrays.asList(aspectVotingDtoStyle, aspectVotingDotOccasion), new ProductRefDto("123", "Collosal Kajal", "Kajal"), new UserRefDto("u123", "username123"));
        AspectVotingDetailsDoc aspectVotingDetailsDoc = aspectVotingDetailsMapper.toDoc(aspectVotingDetailsDto);
        assertEquals("1", aspectVotingDetailsDoc.getAspectVotingDocList().get(0).getAspectId());
        assertEquals("Retro", aspectVotingDetailsDoc.getAspectVotingDocList().get(0).getSelectedOptions().get(0));
        assertEquals("Chic", aspectVotingDetailsDoc.getAspectVotingDocList().get(0).getSelectedOptions().get(1));
        assertEquals("2", aspectVotingDetailsDoc.getAspectVotingDocList().get(1).getAspectId());
        assertEquals("Wedding", aspectVotingDetailsDoc.getAspectVotingDocList().get(1).getSelectedOptions().get(0));
        assertEquals("Party", aspectVotingDetailsDoc.getAspectVotingDocList().get(1).getSelectedOptions().get(1));
        assertEquals("123", aspectVotingDetailsDoc.getProductRef().getId());
        assertEquals("Collosal Kajal", aspectVotingDetailsDoc.getProductRef().getName());
        assertEquals("Kajal", aspectVotingDetailsDoc.getProductRef().getType());
        assertEquals("u123", aspectVotingDetailsDoc.getUserRef().getId());
        assertEquals("username123", aspectVotingDetailsDoc.getUserRef().getUsername());
    }
}