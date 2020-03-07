package com.hexlindia.drool.user.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.business.api.to.ContributionSummaryDto;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.dto.VideoThumbnailDataDto;
import com.hexlindia.drool.video.dto.VideoThumbnailDto;
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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserProfileViewRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class UserProfileViewRestServiceTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserProfile userProfileMock;

    @Test
    public void getContributionSummary_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getContributionSummaryUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getContributionSummary_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getContributionSummaryUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getContributionSummary_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getContributionSummaryUri() + "/8"));
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.userProfileMock, times(1)).getContributionSummary(userIdArgumentCaptor.capture());
        assertEquals("8", userIdArgumentCaptor.getValue());
    }

    @Test
    void getContributionSummary_ValidateJsonResponse() throws Exception {
        VideoThumbnailDto videoThumbnailDto = new VideoThumbnailDto();
        videoThumbnailDto.setId("abc123");
        videoThumbnailDto.setSourceId("s123");
        videoThumbnailDto.setTitle("THis is a rest service test video title");
        videoThumbnailDto.setViews("5k");
        videoThumbnailDto.setLikes("345");
        VideoThumbnailDataDto videoThumbnailDataDto = new VideoThumbnailDataDto();
        videoThumbnailDataDto.setTotalVideoCount(100);
        videoThumbnailDataDto.setVideoThumbnailList(Arrays.asList(videoThumbnailDto));

        when(this.userProfileMock.getContributionSummary("123")).thenReturn(new ContributionSummaryDto(videoThumbnailDataDto));
        this.mockMvc.perform(MockMvcRequestBuilders.get(getContributionSummaryUri() + "/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.videoData.totalVideoCount").value(100))
                .andExpect(jsonPath("$.videoData.videoThumbnailList", hasSize(1)))
                .andExpect(jsonPath("$.videoData.videoThumbnailList[0].id").value("abc123"))
                .andExpect(jsonPath("$.videoData.videoThumbnailList[0].sourceId").value("s123"))
                .andExpect(jsonPath("$.videoData.videoThumbnailList[0].title").value("THis is a rest service test video title"))
                .andExpect(jsonPath("$.videoData.videoThumbnailList[0].views").value("5k"))
                .andExpect(jsonPath("$.videoData.videoThumbnailList[0].likes").value("345"));
    }

    private String getContributionSummaryUri() {
        return "/" + restUriVersion + "/view/profile/contributions/id";
    }
}
