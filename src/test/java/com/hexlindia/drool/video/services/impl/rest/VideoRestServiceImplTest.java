package com.hexlindia.drool.video.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.UserRefDto;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VideoRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class VideoRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    Video videoMock;

    @Test
    void insert_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getInsertUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void insert_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insert_missingParameterType() throws Exception {
        VideoDto videoDto = new VideoDto("", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("type", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video type is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insert_missingParameterTitle() throws Exception {
        VideoDto videoDto = new VideoDto("review", "", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("title", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video title is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insert_missingParameterSourceId() throws Exception {
        VideoDto videoDto = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("sourceId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video Source ID is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insert_missingParameterProductRef() throws Exception {
        VideoDto videoDto = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("productRefDtoList", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Product(s) are not tagged for the video", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insert_missingParameterUserRef() throws Exception {
        VideoDto videoDto = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                null);
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User info is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insert_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.insert(any())).thenReturn(null);
        VideoDto videoDto = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoDto> videoDtoArgumentCaptor = ArgumentCaptor.forClass(VideoDto.class);
        verify(this.videoMock, times(1)).insert(videoDtoArgumentCaptor.capture());
        assertEquals("review", videoDtoArgumentCaptor.getValue().getType());
        assertEquals("L'oreal Collosal Kajal Review", videoDtoArgumentCaptor.getValue().getTitle());
        assertEquals("This is a fake video review for L'oreal kajal", videoDtoArgumentCaptor.getValue().getDescription());
        assertEquals("vQ765gh", videoDtoArgumentCaptor.getValue().getSourceId());
        assertEquals("abc", videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getId());
        assertEquals("Loreal Kajal", videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getName());
        assertEquals("kajal", videoDtoArgumentCaptor.getValue().getProductRefDtoList().get(0).getType());
        assertEquals("123", videoDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("shabana", videoDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
    }

    @Test
    void insert_errorInInsertingVideo() throws Exception {
        VideoDto videoDto = new VideoDto("review", "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        doThrow(new DataIntegrityViolationException("")).when(this.videoMock).insert(any());
        String requestBody = objectMapper.writeValueAsString(videoDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertEquals("Not able to perform action at this time. Try again in some time.", mvcResult.getResponse().getContentAsString());
    }

    private String getInsertUri() {
        return "/" + restUriVersion + "/video/insert";
    }
}