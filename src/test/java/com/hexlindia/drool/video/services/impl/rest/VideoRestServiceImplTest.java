package com.hexlindia.drool.video.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.ProductRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.business.api.usecase.Video;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
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

    @Test
    void incrementLikes_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getIncrementLikesUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void incrementLikes_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void incrementLikes_missingParameterUserId() throws Exception {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void incrementLikes_missingParameterVideoId() throws Exception {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("videoId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void incrementLikes_missingParameterVideoTitle() throws Exception {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("videoTitle", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video title is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void incrementLikes_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.incrementLikes(any())).thenReturn("123");
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(this.videoMock, times(1)).incrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
        assertEquals("Dummy video title", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoTitle());
    }

    @Test
    void decrementLikes_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getDecrementLikesUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void decrementLikes_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDecrementLikesUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void decrementLikes_missingParameterUserId() throws Exception {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId("v1");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDecrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void decrementLikes_missingParameterVideoId() throws Exception {
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDecrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("videoId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Video Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void decrementLikes_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.incrementLikes(any())).thenReturn("123");
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDecrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(this.videoMock, times(1)).decrementLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
        assertEquals("987", videoLikeUnlikeDtoArgumentCaptor.getValue().getUserId());
        assertEquals("v1", videoLikeUnlikeDtoArgumentCaptor.getValue().getVideoId());
    }

    @Test
    void insertComment_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getInsertCommentUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void insertComment_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void insertComment_missingParameterPostRefDto() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post details are missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingParameterUserRefDto() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", "video"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User details are missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingParameterComment() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", "video"));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("comment", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }


    @Test
    void insertComment_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.insertComment(any())).thenReturn(null);
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", "video"));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(this.videoMock, times(1)).insertComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals("p123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("This is a test post", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getTitle());
        assertEquals("video", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getType());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("username1", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
        assertEquals("This is a dummy test", videoCommentDtoArgumentCaptor.getValue().getComment());
    }


    private String getInsertUri() {
        return "/" + restUriVersion + "/video/insert";
    }

    private String getIncrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/increment";
    }

    private String getDecrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/decrement";
    }

    private String getInsertCommentUri() {
        return "/" + restUriVersion + "/video/insert/comment";
    }
}