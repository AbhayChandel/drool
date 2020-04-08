package com.hexlindia.drool.activity.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.activity.business.api.usecase.ActivityFeed;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
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

@WebMvcTest(value = ActivityFeedRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class ActivityFeedRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ActivityFeed activityFeedMocked;

    private String getActivityFeedUri() {
        return "/" + restUriVersion + "/view/activity/feed";
    }

    @Test
    public void getFeed_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getActivityFeedUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getFeed_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getActivityFeedUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getFeed_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getActivityFeedUri() + "/0"));

        ArgumentCaptor<Integer> pageArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(this.activityFeedMocked, times(1)).getFeed(pageArgumentCaptor.capture());
        assertEquals(0, pageArgumentCaptor.getValue());
    }

}