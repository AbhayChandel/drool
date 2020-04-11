package com.hexlindia.drool.discussion.services.iml.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.business.api.usecase.DiscussionReply;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import org.bson.types.ObjectId;
import org.json.JSONObject;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
    void saveReply_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getPostUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void saveReply_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveReply_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.discussionReplyMocked.saveReply(any())).thenReturn(null);
        DiscussionReplyDto discussionReplyDtoMocked = new DiscussionReplyDto();
        ObjectId discussionId = new ObjectId();
        discussionReplyDtoMocked.setDiscussionId(discussionId.toHexString());
        discussionReplyDtoMocked.setReply(("this is a new reply"));
        ObjectId userId = new ObjectId();
        discussionReplyDtoMocked.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));
        String requestBody = objectMapper.writeValueAsString(discussionReplyDtoMocked);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<DiscussionReplyDto> discussionReplyDtoArgumentCaptor = ArgumentCaptor.forClass(DiscussionReplyDto.class);
        verify(this.discussionReplyMocked, times(1)).saveReply(discussionReplyDtoArgumentCaptor.capture());
        assertEquals("this is a new reply", discussionReplyDtoArgumentCaptor.getValue().getReply());
        assertEquals(discussionId.toHexString(), discussionReplyDtoArgumentCaptor.getValue().getDiscussionId());
        assertEquals(userId.toHexString(), discussionReplyDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("shabana", discussionReplyDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
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
    void update_ParametersArePassedToBusinessLayer() throws Exception {
        String reply = "THis is a usual reply";
        ObjectId replyId = new ObjectId();
        ObjectId discussionId = new ObjectId();
        when(this.discussionReplyMocked.updateReply(reply, replyId.toHexString(), discussionId.toHexString())).thenReturn(true);
        JSONObject requestBody = new JSONObject();
        requestBody.put("reply", reply);
        requestBody.put("replyId", replyId.toHexString());
        requestBody.put("discussionId", discussionId.toHexString());
        //String requestBody = "{\"reply\": \"" + reply + "\", \"replyId\": \"" + replyId.toHexString() + "\", \"discussionId\": \"" + discussionId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody.toString()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<String> replyArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> replyIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> discussionIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionReplyMocked, times(1)).updateReply(replyArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertEquals("THis is a usual reply", replyArgumentCaptor.getValue());
        assertEquals(replyId.toHexString(), replyIdArgumentCaptor.getValue());
        assertEquals(discussionId.toHexString(), discussionIdArgumentCaptor.getValue());
    }

    @Test
    public void incrementLikes_ParametersPassedToBusinessLayer() throws Exception {
        Integer likes = 300;
        ObjectId replyId = new ObjectId();
        ObjectId discussionId = new ObjectId();
        ObjectId userId = new ObjectId();
        when(this.discussionReplyMocked.incrementLikes(replyId.toHexString(), discussionId.toHexString(), userId.toHexString())).thenReturn(null);
        String requestBody = "{\"replyId\": \"" + replyId.toHexString() + "\", \"discussionId\": \"" + discussionId.toHexString() + "\", \"userId\": \"" + userId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesIncrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<String> replyIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> discussionIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionReplyMocked, times(1)).incrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture(), userIdArgumentCaptor.capture());
        assertEquals(replyId.toHexString(), replyIdArgumentCaptor.getValue());
        assertEquals(discussionId.toHexString(), discussionIdArgumentCaptor.getValue());
        assertEquals(userId.toHexString(), userIdArgumentCaptor.getValue());
    }

    @Test
    public void decrementLikes_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId replyId = new ObjectId();
        ObjectId discussionId = new ObjectId();
        ObjectId userId = new ObjectId();
        when(this.discussionReplyMocked.decrementLikes(replyId.toHexString(), discussionId.toHexString(), userId.toHexString())).thenReturn(null);
        String requestBody = "{\"replyId\": \"" + replyId.toHexString() + "\", \"discussionId\": \"" + discussionId.toHexString() + "\", \"userId\": \"" + userId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getLikesDecrementUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<String> replyIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> discussionIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionReplyMocked, times(1)).decrementLikes(replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture(), userIdArgumentCaptor.capture());
        assertEquals(replyId.toHexString(), replyIdArgumentCaptor.getValue());
        assertEquals(discussionId.toHexString(), discussionIdArgumentCaptor.getValue());
        assertEquals(userId.toHexString(), userIdArgumentCaptor.getValue());
    }

    @Test
    void setStatus_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getSetStatusUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void setStatus_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getSetStatusUri()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void setStatus_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId replyId = new ObjectId();
        ObjectId discussionId = new ObjectId();
        Boolean status = true;
        when(this.discussionReplyMocked.setStatus(status, replyId.toHexString(), discussionId.toHexString())).thenReturn(true);
        String requestBody = "{\"status\":\"" + status + "\", \"replyId\": \"" + replyId.toHexString() + "\", \"discussionId\": \"" + discussionId.toHexString() + "\"}";
        this.mockMvc.perform(MockMvcRequestBuilders.put(getSetStatusUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        ArgumentCaptor<Boolean> statusArgumentCaptor = ArgumentCaptor.forClass(Boolean.class);
        ArgumentCaptor<String> replyIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> discussionIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.discussionReplyMocked, times(1)).setStatus(statusArgumentCaptor.capture(), replyIdArgumentCaptor.capture(), discussionIdArgumentCaptor.capture());
        assertTrue(statusArgumentCaptor.getValue());
        assertEquals(replyId.toHexString(), replyIdArgumentCaptor.getValue());
        assertEquals(discussionId.toHexString(), discussionIdArgumentCaptor.getValue());
    }


    private String getPostUri() {
        return "/" + restUriVersion + "/discussion/reply/post";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/reply/update";
    }

    private String getSetStatusUri() {
        return "/" + restUriVersion + "/discussion/reply/set";
    }

    private String getLikesIncrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/increment";
    }

    private String getLikesDecrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/decrement";
    }
}