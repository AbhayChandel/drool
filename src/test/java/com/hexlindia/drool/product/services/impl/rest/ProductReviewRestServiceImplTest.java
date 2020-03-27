package com.hexlindia.drool.product.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.dto.AspectPreferenceDto;
import com.hexlindia.drool.product.dto.BrandRatingDto;
import com.hexlindia.drool.product.dto.ReviewDto;
import com.hexlindia.drool.product.dto.TextReviewDto;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.dto.VideoDto;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductReviewRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class ProductReviewRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductReview productReviewMocked;

    private String getSaveUri() {
        return "/" + restUriVersion + "/product/review/save";
    }

    @Test
    public void saveReview_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveUri()))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    void save_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getSaveUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void save_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.productReviewMocked.save(any())).thenReturn(null);
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setReviewType(ReviewType.text);

        AspectPreferenceDto aspectPreferenceDtoStyle = new AspectPreferenceDto();
        aspectPreferenceDtoStyle.setAspectId("abc");
        aspectPreferenceDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Bohemian"));
        AspectPreferenceDto aspectPreferenceDtoOcassion = new AspectPreferenceDto();
        aspectPreferenceDtoOcassion.setAspectId("def");
        aspectPreferenceDtoOcassion.setSelectedOptions(Arrays.asList("Wedding", "Cocktail"));
        reviewDto.setAspectPreferenceDtoList(Arrays.asList(aspectPreferenceDtoOcassion, aspectPreferenceDtoStyle));

        BrandRatingDto brandRatingDtoTrendy = new BrandRatingDto();
        brandRatingDtoTrendy.setName("Trendy");
        brandRatingDtoTrendy.setRating(4);
        reviewDto.setBrandRatingDtoList(Arrays.asList(brandRatingDtoTrendy));

        reviewDto.setRecommendation("1");

        reviewDto.setProductRefDto(new ProductRefDto("123", "Colossal Kajal", "Kajal"));

        TextReviewDto textReviewDto = new TextReviewDto();
        textReviewDto.setDetailedReview("THis is a details text review");
        textReviewDto.setReviewSummary("This is text review summary");
        reviewDto.setTextReviewDto(textReviewDto);

        VideoDto videoDto = new VideoDto();
        videoDto.setId("abc");
        videoDto.setType("review");
        videoDto.setActive(true);
        videoDto.setTitle("The is a mocked video review");
        videoDto.setDescription("this is a mocked video review description");
        videoDto.setSourceId("werlkj");
        reviewDto.setVideoDto(videoDto);

        reviewDto.setUserRefDto(new UserRefDto("1", "shabana"));

        String requestBody = objectMapper.writeValueAsString(reviewDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getSaveUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<ReviewDto> reviewDtoArgumentCaptor = ArgumentCaptor.forClass(ReviewDto.class);
        verify(this.productReviewMocked, times(1)).save(reviewDtoArgumentCaptor.capture());
        ReviewDto reviewDtoPaased = reviewDtoArgumentCaptor.getValue();
        assertEquals(ReviewType.text, reviewDtoPaased.getReviewType());

        assertEquals("abc", reviewDtoPaased.getAspectPreferenceDtoList().get(1).getAspectId());
        assertEquals("Retro", reviewDtoPaased.getAspectPreferenceDtoList().get(1).getSelectedOptions().get(0));
        assertEquals("Bohemian", reviewDtoPaased.getAspectPreferenceDtoList().get(1).getSelectedOptions().get(1));

        assertEquals("def", reviewDtoPaased.getAspectPreferenceDtoList().get(0).getAspectId());
        assertEquals("Wedding", reviewDtoPaased.getAspectPreferenceDtoList().get(0).getSelectedOptions().get(0));
        assertEquals("Cocktail", reviewDtoPaased.getAspectPreferenceDtoList().get(0).getSelectedOptions().get(1));

        assertEquals("Trendy", reviewDtoPaased.getBrandRatingDtoList().get(0).getName());
        assertEquals(4, reviewDtoPaased.getBrandRatingDtoList().get(0).getRating());

        assertEquals("1", reviewDtoPaased.getRecommendation());

        assertEquals("123", reviewDtoPaased.getProductRefDto().getId());
        assertEquals("Colossal Kajal", reviewDtoPaased.getProductRefDto().getName());
        assertEquals("Kajal", reviewDtoPaased.getProductRefDto().getType());

        assertEquals("THis is a details text review", reviewDtoPaased.getTextReviewDto().getDetailedReview());
        assertEquals("This is text review summary", reviewDtoPaased.getTextReviewDto().getReviewSummary());

        assertEquals("abc", reviewDtoPaased.getVideoDto().getId());
        assertEquals("review", reviewDtoPaased.getVideoDto().getType());
        assertTrue(reviewDtoPaased.getVideoDto().isActive());
        assertEquals("The is a mocked video review", reviewDtoPaased.getVideoDto().getTitle());
        assertEquals("this is a mocked video review description", reviewDtoPaased.getVideoDto().getDescription());
        assertEquals("werlkj", reviewDtoPaased.getVideoDto().getSourceId());

        assertEquals("1", reviewDtoPaased.getUserRefDto().getId());
        assertEquals("shabana", reviewDtoPaased.getUserRefDto().getUsername());
    }
}