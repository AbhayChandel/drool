package com.hexlindia.drool.product.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.business.api.usecase.ProductReview;
import com.hexlindia.drool.product.business.impl.usecase.ReviewType;
import com.hexlindia.drool.product.dto.*;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.dto.VideoDto;
import org.bson.types.ObjectId;
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

    private String getAspectTemplatesUri() {
        return "/" + restUriVersion + "/product/review/forms";
    }

    @Test
    public void getAspectTemplates_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getAspectTemplatesUri() + "/1/2"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getAspectTemplates_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getAspectTemplatesUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAspectTemplates_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId productId = ObjectId.get();
        ObjectId brandId = ObjectId.get();
        this.mockMvc.perform(MockMvcRequestBuilders.get(getAspectTemplatesUri() + "/" + productId + "/" + brandId));

        ArgumentCaptor<ObjectId> productIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        ArgumentCaptor<ObjectId> brandIdArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.productReviewMocked, times(1)).getReviewDialogForms(productIdArgumentCaptor.capture(), brandIdArgumentCaptor.capture());
        assertEquals(productId, productIdArgumentCaptor.getValue());
        assertEquals(brandId, brandIdArgumentCaptor.getValue());
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

        AspectVotingDto aspectVotingDtoStyle = new AspectVotingDto();
        aspectVotingDtoStyle.setAspectId("abc");
        aspectVotingDtoStyle.setSelectedOptions(Arrays.asList("Retro", "Bohemian"));
        AspectVotingDto aspectVotingDtoOcassion = new AspectVotingDto();
        aspectVotingDtoOcassion.setAspectId("def");
        aspectVotingDtoOcassion.setSelectedOptions(Arrays.asList("Wedding", "Cocktail"));
        reviewDto.setAspectVotingDtoList(Arrays.asList(aspectVotingDtoOcassion, aspectVotingDtoStyle));

        BrandRatingMetricDto brandRatingMetricDtoTrendy = new BrandRatingMetricDto();
        brandRatingMetricDtoTrendy.setName("Trendy");
        brandRatingMetricDtoTrendy.setRating(4);

        BrandRatingsDetailsDto brandRatingsDetailsDto = new BrandRatingsDetailsDto(null, null, Arrays.asList(brandRatingMetricDtoTrendy), new BrandRefDto(ObjectId.get().toHexString(), "Lakme"), new UserRefDto("u123", "username123"));
        reviewDto.setBrandRatingsDetailsDto(brandRatingsDetailsDto);

        reviewDto.setRecommendation("1");

        reviewDto.setProductRefDto(new ProductRefDto("123", "Colossal Kajal", "Kajal"));

        TextReviewDto textReviewDto = new TextReviewDto();
        textReviewDto.setDetailedReview("THis is a details text review");
        textReviewDto.setReviewSummary("This is text review summary");
        reviewDto.setTextReviewDto(textReviewDto);

        VideoDto videoDto = new VideoDto();
        videoDto.setId("abc");
        videoDto.setType(PostType.review);
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

        assertEquals("abc", reviewDtoPaased.getAspectVotingDtoList().get(1).getAspectId());
        assertEquals("Retro", reviewDtoPaased.getAspectVotingDtoList().get(1).getSelectedOptions().get(0));
        assertEquals("Bohemian", reviewDtoPaased.getAspectVotingDtoList().get(1).getSelectedOptions().get(1));

        assertEquals("def", reviewDtoPaased.getAspectVotingDtoList().get(0).getAspectId());
        assertEquals("Wedding", reviewDtoPaased.getAspectVotingDtoList().get(0).getSelectedOptions().get(0));
        assertEquals("Cocktail", reviewDtoPaased.getAspectVotingDtoList().get(0).getSelectedOptions().get(1));

        assertEquals("Trendy", reviewDtoPaased.getBrandRatingsDetailsDto().getBrandRatingMetricDtoList().get(0).getName());
        assertEquals(4, reviewDtoPaased.getBrandRatingsDetailsDto().getBrandRatingMetricDtoList().get(0).getRating());

        assertEquals("1", reviewDtoPaased.getRecommendation());

        assertEquals("123", reviewDtoPaased.getProductRefDto().getId());
        assertEquals("Colossal Kajal", reviewDtoPaased.getProductRefDto().getName());
        assertEquals("Kajal", reviewDtoPaased.getProductRefDto().getType());

        assertEquals("THis is a details text review", reviewDtoPaased.getTextReviewDto().getDetailedReview());
        assertEquals("This is text review summary", reviewDtoPaased.getTextReviewDto().getReviewSummary());

        assertEquals("abc", reviewDtoPaased.getVideoDto().getId());
        assertEquals(PostType.review, reviewDtoPaased.getVideoDto().getType());
        assertTrue(reviewDtoPaased.getVideoDto().isActive());
        assertEquals("The is a mocked video review", reviewDtoPaased.getVideoDto().getTitle());
        assertEquals("this is a mocked video review description", reviewDtoPaased.getVideoDto().getDescription());
        assertEquals("werlkj", reviewDtoPaased.getVideoDto().getSourceId());

        assertEquals("1", reviewDtoPaased.getUserRefDto().getId());
        assertEquals("shabana", reviewDtoPaased.getUserRefDto().getUsername());
    }
}