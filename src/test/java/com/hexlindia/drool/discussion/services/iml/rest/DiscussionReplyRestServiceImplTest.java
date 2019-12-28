package com.hexlindia.drool.discussion.services.iml.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.common.to.ActivityTo;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
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

@WebMvcTest(value = DiscussionReplyRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class DiscussionReplyRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    DiscussionReply discussionReplyMocked;

    @Test
    void post_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getPostUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void post_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void post_missingParameterDiscussionTopicId() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, null, "This is a reply", 7L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("discussionTopicId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Discussion Id cannot be null", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void post_missingParameterReply() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, 2L, "", 7L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("reply", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Reply cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void post_missingParameterUserId() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, 2L, "There is a reply", null);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id cannot be null", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void post_allParametersValid() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, 10L, "This is a dummy reply", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void post_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.discussionReplyMocked.post(any())).thenReturn(null);
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, 10L, "This is a dummy reply", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<DiscussionReplyTo> discussionReplyToArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyTo.class);
        verify(this.discussionReplyMocked, times(1)).post(discussionReplyToArgumentCaptor.capture());
        assertEquals("This is a dummy reply", discussionReplyToArgumentCaptor.getValue().getReply());
    }

    @Test
    void post_errorInPostingReply() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, 10L, "This is a dummy reply", 3L);
        doThrow(new DataIntegrityViolationException("")).when(this.discussionReplyMocked).post(any());
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
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
    void update_missingParameterId() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(null, null, "THere is a reply", null);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Reply Id cannot be null", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void update_missingParameterReply() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(7L, null, "", null);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("reply", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Reply cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void update_allParametersValid() throws Exception {
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(4L, 10L, "This is a dummy reply", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void update_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.discussionReplyMocked.post(any())).thenReturn(null);
        DiscussionReplyTo discussionReplyTo = new DiscussionReplyTo(5L, 10L, "This is a dummy reply", 3L);
        String requestBody = objectMapper.writeValueAsString(discussionReplyTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<DiscussionReplyTo> discussionReplyToArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyTo.class);
        verify(this.discussionReplyMocked, times(1)).updateReply(discussionReplyToArgumentCaptor.capture());
        assertEquals("This is a dummy reply", discussionReplyToArgumentCaptor.getValue().getReply());
        assertEquals(5L, discussionReplyToArgumentCaptor.getValue().getId());
    }

    @Test
    void deleteReplyById_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getDeleteByIdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteReplyById_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteByIdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteReplById_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete(getDeleteByIdUri() + "/101"));

        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.discussionReplyMocked, times(1)).deactivateReply(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
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
        verify(this.discussionReplyMocked, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(101L, idArgumentCaptor.getValue());
    }

    @Test
    void findById_ValidateJsonResponse() throws Exception {
        DiscussionReplyTo discussionReplyToMocked = new DiscussionReplyTo(5L, 10L, "This is a dummy reply", 3L);
        when(this.discussionReplyMocked.findById(5L)).thenReturn(discussionReplyToMocked);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindByIdUri() + "/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.discussionTopicId").value("10"))
                .andExpect(jsonPath("$.reply").value("This is a dummy reply"))
                .andExpect(jsonPath("$.userId").value("3"));
    }

    @Test
    public void incrementLikesCount_ParametersPassedToBusinessLayer() throws Exception {
        ActivityTo activityTo = new ActivityTo(10L, 20L);
        String requestBody = objectMapper.writeValueAsString(activityTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesIncrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<ActivityTo> activityToArgumentCaptor = ArgumentCaptor.forClass(ActivityTo.class);
        verify(this.discussionReplyMocked, times(1)).incrementLikesByOne(activityToArgumentCaptor.capture());
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
        verify(this.discussionReplyMocked, times(1)).decrementLikesByOne(activityToArgumentCaptor.capture());
        assertEquals(10L, activityToArgumentCaptor.getValue().getPostId());
        assertEquals(20L, activityToArgumentCaptor.getValue().getCurrentUserId());
    }


    private String getPostUri() {
        return "/" + restUriVersion + "/discussion/reply/post";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/reply/update";
    }

    private String getDeleteByIdUri() {
        return "/" + restUriVersion + "/discussion/reply/delete/id";
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/discussion/reply/find/id";
    }

    private String getLikesIncrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/increment";
    }

    private String getLikesDecrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/decrement";
    }
}