package com.hexlindia.drool.discussion.services.iml.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionView;
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

@WebMvcTest(value = DiscussionViewRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class DiscussionViewRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DiscussionView discussionView;

    @Test
    public void findDiscussionTopicCardViewById_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getfindDiscussionTopicCardViewByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findDiscussionTopicCardViewById_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getfindDiscussionTopicCardViewByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findDiscussionTopicCardViewById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getfindDiscussionTopicCardViewByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionView, times(1)).getDicussionTopicCardView(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    @Test
    public void findDiscussionPageViewById_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getfindDiscussionPageViewByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findDiscussionPageViewById_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getfindDiscussionPageViewByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findDiscussionPageViewById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getfindDiscussionPageViewByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionView, times(1)).getDiscussionPageView(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    private String getfindDiscussionTopicCardViewByIdUri() {
        return "/" + restUriVersion + "/view/discussion/topic/id";
    }

    private String getfindDiscussionPageViewByIdUri() {
        return "/" + restUriVersion + "/view/discussion/page/id";
    }
}