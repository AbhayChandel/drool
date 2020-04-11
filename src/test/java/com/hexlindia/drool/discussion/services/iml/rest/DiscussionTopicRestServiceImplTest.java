package com.hexlindia.drool.discussion.services.iml.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionTopic;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    void post_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.discussionTopicMocked.post(any())).thenReturn(null);
        DiscussionTopicDto discussionTopicDtoMocked = new DiscussionTopicDto();
        discussionTopicDtoMocked.setTitle("THis is a test discusion title");
        discussionTopicDtoMocked.setLikes("10");
        discussionTopicDtoMocked.setViews("20");
        ObjectId userId = new ObjectId();
        discussionTopicDtoMocked.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));
        String requestBody = objectMapper.writeValueAsString(discussionTopicDtoMocked);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<DiscussionTopicDto> discussionTopicDtoArgumentCaptor = ArgumentCaptor.forClass(DiscussionTopicDto.class);
        verify(this.discussionTopicMocked, times(1)).post(discussionTopicDtoArgumentCaptor.capture());
        assertEquals("THis is a test discusion title", discussionTopicDtoArgumentCaptor.getValue().getTitle());
        assertEquals("10", discussionTopicDtoArgumentCaptor.getValue().getLikes());
        assertEquals("20", discussionTopicDtoArgumentCaptor.getValue().getViews());
        assertEquals(userId.toHexString(), discussionTopicDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("shabana", discussionTopicDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
    }

    @Test
    void updateTitle_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getUpdateUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void updateTitle_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void incrementViews_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId idMocked = new ObjectId();
        this.mockMvc.perform(MockMvcRequestBuilders.put(getViewsIncrementUri())
                .content(idMocked.toHexString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionTopicMocked, times(1)).incrementViews(idArgumentCaptor.capture());
        assertEquals(idMocked.toHexString(), idArgumentCaptor.getValue());
    }

    @Test
    public void incrementLikes_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId id = new ObjectId();
        ObjectId userId = new ObjectId();

        String requestBody = "{\"id\": \"" + id.toHexString() + "\", \"userId\": \"" + userId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesIncrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionTopicMocked, times(1)).incrementLikes(idArgumentCaptor.capture(), userIdArgumentCaptor.capture());
        assertEquals(id.toHexString(), idArgumentCaptor.getValue());
        assertEquals(userId.toHexString(), userIdArgumentCaptor.getValue());
    }

    @Test
    public void decrementLikes_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId id = new ObjectId();
        ObjectId userId = new ObjectId();

        String temp = objectMapper.writeValueAsString(new UserRefDto("abc", "shababa"));

        String requestBody = "{\"id\": \"" + id.toHexString() + "\", \"userId\": \"" + userId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesDecrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionTopicMocked, times(1)).decrementLikes(idArgumentCaptor.capture(), userIdArgumentCaptor.capture());
        assertEquals(id.toHexString(), idArgumentCaptor.getValue());
        assertEquals(userId.toHexString(), userIdArgumentCaptor.getValue());
    }


    private String getPostUri() {
        return "/" + restUriVersion + "/discussion/post";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/update";
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