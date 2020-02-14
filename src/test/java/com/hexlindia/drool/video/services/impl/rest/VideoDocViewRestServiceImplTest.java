package com.hexlindia.drool.video.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.business.api.usecase.VideoView;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = VideoViewRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class VideoDocViewRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    VideoView videoViewMocked;

    @Test
    public void findVideoCardViewById_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getfindVideoCardViewByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findVideoCardViewById_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getfindVideoCardViewByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findVideoCardViewById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getfindVideoCardViewByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.videoViewMocked, times(1)).getVideoCardView(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    @Test
    public void findVideoPageViewById_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getfindVideoPageViewByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findVideoPageViewById_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getfindVideoPageViewByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findVideoPageViewById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getfindVideoPageViewByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.videoViewMocked, times(1)).getVideoPageView(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    private String getfindVideoCardViewByIdUri() {
        return "/" + restUriVersion + "/view/video/card/id";
    }

    private String getfindVideoPageViewByIdUri() {
        return "/" + restUriVersion + "/view/video/page/id";
    }
}