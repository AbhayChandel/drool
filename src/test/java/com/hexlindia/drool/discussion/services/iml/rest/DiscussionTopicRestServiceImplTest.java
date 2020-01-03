package com.hexlindia.drool.discussion.services.iml.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = DiscussionTopicRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class DiscussionTopicRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DiscussionTopic discussionTopicMocked;

    @Test
    void create_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getCreateUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void create_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_missingParameterTopic() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("topic", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Topic title cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void create_missingParameterUserId() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "THis is topic", null);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id cannot be null", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void create_allParametersValid() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "THis is another topic", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void create_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.discussionTopicMocked.post(any())).thenReturn(null);
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "THis is another topic", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<DiscussionTopicTo> discussionTopicToArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicTo.class);
        verify(this.discussionTopicMocked, times(1)).post(discussionTopicToArgumentCaptor.capture());
        assertEquals("THis is another topic", discussionTopicToArgumentCaptor.getValue().getTopic());
    }

    @Test
    void create_errorInCreatingDiscussion() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "THis is another topic", 3L);
        doThrow(new DataIntegrityViolationException("")).when(this.discussionTopicMocked).post(any());
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getCreateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertEquals("Not able to create/update at this time. Try again in some time", mvcResult.getResponse().getContentAsString());
    }

    @Test
    void update_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getUpdateUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void update_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_missingParameterTopic() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(6L, "", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("topic", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Topic title cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void update_missingParameterId() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(null, "THis is topic", null);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Discussion Id cannot be null", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void update_allParametersValid() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(4L, "THis is another topic", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionTopicTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findById_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getFindByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }


    @Test
    public void findById_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getFindByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionTopicMocked, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    @Test
    void findByEmail_ValidateJsonResponse() throws Exception {
        DiscussionTopicTo discussionTopicTo = new DiscussionTopicTo(5L, "A common topic.", 19L);
        when(this.discussionTopicMocked.findById(5L)).thenReturn(discussionTopicTo);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindByIdUri() + "/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"));
    }

    @Test
    public void incrementViewsCount_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getViewsIncrementUri())
                .content("10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionTopicMocked, times(1)).incrementViewsByOne(idArgumentCaptor.capture());
        assertEquals(10L, idArgumentCaptor.getValue());
    }

    @Test
    public void incrementLikesCount_ParametersPassedToBusinessLayer() throws Exception {
        ActivityTo activityTo = new ActivityTo(10L, 20L);
        String requestBody = objectMapper.writeValueAsString(activityTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesIncrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<ActivityTo> activityToArgumentCaptor = ArgumentCaptor.forClass(ActivityTo.class);
        verify(this.discussionTopicMocked, times(1)).incrementLikesByOne(activityToArgumentCaptor.capture());
        assertEquals(10L, activityToArgumentCaptor.getValue().getPostId());
        assertEquals(20L, activityToArgumentCaptor.getValue().getCurrentUserId());
    }

    @Test
    public void decrementLikesCount_ParametersPassedToBusinessLayer() throws Exception {
        ActivityTo activityTo = new ActivityTo(10L, 20L);
        String requestBody = objectMapper.writeValueAsString(activityTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesDecrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<ActivityTo> activityToArgumentCaptor = ArgumentCaptor.forClass(ActivityTo.class);
        verify(this.discussionTopicMocked, times(1)).decrementLikesByOne(activityToArgumentCaptor.capture());
        assertEquals(10L, activityToArgumentCaptor.getValue().getPostId());
        assertEquals(20L, activityToArgumentCaptor.getValue().getCurrentUserId());
    }


    private String getCreateUri() {
        return "/" + restUriVersion + "/discussion/post";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/update";
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/discussion/find/id";
    }

    private String getViewsIncrementUri() {
        return "/" + restUriVersion + "/discussion/views/increment";
    }

    private String getLikesIncrementUri() {
        return "/" + restUriVersion + "/discussion/likes/increment";
    }

    private String getLikesDecrementUri() {
        return "/" + restUriVersion + "/discussion/likes/decrement";
    }

}