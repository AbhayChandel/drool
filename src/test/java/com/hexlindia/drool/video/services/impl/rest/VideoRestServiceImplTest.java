package com.hexlindia.drool.video.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.product.dto.ProductRefDto;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.video.business.api.Video;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
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
    void save_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getInsertUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void save_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void save_missingParameterType() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(null, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
    void save_missingParameterTitle() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
    void save_missingParameterSourceId() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
    void save_missingParameterProductRef() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
    void save_missingParameterUserRef() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                null);
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
    void save_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.saveOrUpdate(any())).thenReturn(null);
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoDtoMOngo> videoDtoArgumentCaptor = ArgumentCaptor.forClass(VideoDtoMOngo.class);
        //FIXME
        //verify(this.videoMock, times(1)).saveOrUpdate(videoDtoArgumentCaptor.capture());
        assertEquals(PostType.review, videoDtoArgumentCaptor.getValue().getType());
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
    void delete_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.delete(any())).thenReturn(true);
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
        this.mockMvc.perform(MockMvcRequestBuilders.delete(getDeleteUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoDtoMOngo> videoDtoArgumentCaptor = ArgumentCaptor.forClass(VideoDtoMOngo.class);
        //FIXME
        //verify(this.videoMock, times(1)).delete(videoDtoArgumentCaptor.capture());
        assertEquals(PostType.review, videoDtoArgumentCaptor.getValue().getType());
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
    void save_errorInInsertingVideo() throws Exception {
        VideoDtoMOngo videoDtoMOngo = new VideoDtoMOngo(PostType.review, "L'oreal Collosal Kajal Review", "This is a fake video review for L'oreal kajal", "vQ765gh",
                Arrays.asList(new ProductRefDto("abc", "Loreal Kajal", "kajal")),
                new UserRefDto("123", "shabana"));
        doThrow(new DataIntegrityViolationException("")).when(this.videoMock).saveOrUpdate(any());
        String requestBody = objectMapper.writeValueAsString(videoDtoMOngo);
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
        when(this.videoMock.incrementVideoLikes(any())).thenReturn("123");
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        videoLikeUnlikeDto.setVideoTitle("Dummy video title");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getIncrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(this.videoMock, times(1)).incrementVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
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
        when(this.videoMock.incrementVideoLikes(any())).thenReturn("123");
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setUserId("987");
        videoLikeUnlikeDto.setVideoId("v1");
        String requestBody = objectMapper.writeValueAsString(videoLikeUnlikeDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDecrementLikesUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoLikeUnlikeDto> videoLikeUnlikeDtoArgumentCaptor = ArgumentCaptor.forClass(VideoLikeUnlikeDto.class);
        verify(this.videoMock, times(1)).decrementVideoLikes(videoLikeUnlikeDtoArgumentCaptor.capture());
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
    void insertComment_missingParameterObjectPostRefDto() throws Exception {
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
    void insertComment_missingPostRefDtoParameterId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("", "This is dummy post", PostType.guide, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingPostRefDtoParameterTitle() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "", PostType.guide, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.title", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post title is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingPostRefDtoParameterType() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is dummy post", null, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.type", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post type is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingPostRefDtoParameterMedium() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is dummy post", PostType.guide, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.medium", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post medium is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingParameterUserRefDto() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null));
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
    void insertComment_missingUserRefDtoParameterId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingUserRefDtoParameterUsername() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", ""));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto.username", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Username is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void insertComment_missingParameterComment() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null));
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
        when(this.videoMock.insertOrUpdateComment(any())).thenReturn(null);
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getInsertCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(this.videoMock, times(1)).insertOrUpdateComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals("p123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("This is a test post", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getTitle());
        assertEquals(PostType.guide, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getType());
        assertEquals(PostFormat.video, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getMedium());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("username1", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
        assertEquals("This is a dummy test", videoCommentDtoArgumentCaptor.getValue().getComment());
    }

    @Test
    void deleteComment_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getDeleteCommentUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteComment_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteComment_missingParameterCommentId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setPostRefDto(new PostRefDto("p123", null, null, null, null));
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void deleteComment_missingParameterPostId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setPostRefDto(new PostRefDto("", null, null, null, null));
        videoCommentDto.setId("c123");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void deleteComment_missingParameterUserId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setUserRefDto(new UserRefDto("", null));
        videoCommentDto.setPostRefDto(new PostRefDto("p123", null, null, null, null));
        videoCommentDto.setId("c123");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }


    @Test
    void deleteComment_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.deleteComment(any())).thenReturn(true);
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setId("c123");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(this.videoMock, times(1)).deleteComment(videoCommentDtoArgumentCaptor.capture());
        assertEquals("p123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
    }

    @Test
    void saveCommentLike_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getSaveCommentLikeUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void saveCommentLike_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveCommentLike_missingParameterCommentId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null),
                new UserRefDto("u123", "username1"), "This is a test comment");
        videoCommentDto.setId("");
        videoCommentDto.setLikes("1");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void saveCommentLike_missingParameterPostRefDto() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(null,
                new UserRefDto("u123", "username1"), "This is a test comment");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("1");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post details are missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void saveCommentLike_missingParameterUserRefDto() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null),
                null, "This is a test comment");
        videoCommentDto.setId("v123");
        videoCommentDto.setLikes("1");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User details are missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void saveCommentLike_missingParameterComment() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null),
                new UserRefDto("u123", "username1"), "");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("1");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("comment", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void saveCommentLike_missingParameterLikes() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null),
                new UserRefDto("u123", "username1"), "This is a test comment");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("likes", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment likes is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void saveCommentLike_passingParametersToBusinessLayer() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", "This is a test post", PostType.guide, PostFormat.video, null),
                new UserRefDto("u123", "username1"), "This is a test comment");
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("5");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getSaveCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(this.videoMock, times(1)).saveCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("p123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("This is a test post", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getTitle());
        assertEquals(PostType.guide, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getType());
        assertEquals(PostFormat.video, videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getMedium());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("username1", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getUsername());
        assertEquals("This is a test comment", videoCommentDtoArgumentCaptor.getValue().getComment());
        assertEquals("5", videoCommentDtoArgumentCaptor.getValue().getLikes());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());

    }

    @Test
    void deleteCommentLike_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getDeleteCommentLikeUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void deleteCommentLike_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentLikeUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCommentLike_missingParameterCommentId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", null, null, null, null),
                new UserRefDto("u123", null), null);
        videoCommentDto.setId("");
        videoCommentDto.setLikes("9");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Comment Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void deleteCommentLike_missingParameterPostId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("", null, null, null, null),
                new UserRefDto("u123", null), null);
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("9");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("postRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Post Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void deleteCommentLike_missingParameterUserId() throws Exception {
        VideoCommentDto videoCommentDto = new VideoCommentDto(new PostRefDto("p123", null, null, null, null),
                new UserRefDto("", null), null);
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("9");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userRefDto.id", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void deleteCommentLike_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.videoMock.deleteCommentLike(any())).thenReturn(null);
        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto("p123", null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setId("c123");
        videoCommentDto.setLikes("9");
        String requestBody = objectMapper.writeValueAsString(videoCommentDto);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getDeleteCommentLikeUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<VideoCommentDto> videoCommentDtoArgumentCaptor = ArgumentCaptor.forClass(VideoCommentDto.class);
        verify(this.videoMock, times(1)).deleteCommentLike(videoCommentDtoArgumentCaptor.capture());
        assertEquals("p123", videoCommentDtoArgumentCaptor.getValue().getPostRefDto().getId());
        assertEquals("u123", videoCommentDtoArgumentCaptor.getValue().getUserRefDto().getId());
        assertEquals("c123", videoCommentDtoArgumentCaptor.getValue().getId());
    }


    private String getInsertUri() {
        return "/" + restUriVersion + "/video/save";
    }

    private String getDeleteUri() {
        return "/" + restUriVersion + "/video/delete";
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

    private String getDeleteCommentUri() {
        return "/" + restUriVersion + "/video/delete/comment";
    }

    private String getSaveCommentLikeUri() {
        return "/" + restUriVersion + "/video/comment/likes/increment";
    }

    private String getDeleteCommentLikeUri() {
        return "/" + restUriVersion + "/video/comment/likes/decrement";
    }

}