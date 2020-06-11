package com.hexlindia.drool.post.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.post.business.api.Post;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = PostRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class PostRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Post post;

    @Test
    void title_missing() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setType(PostType2.ARTICLE);
        postDto.setOwnerId("1009");
        String requestBody = objectMapper.writeValueAsString(postDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertOrUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("Title is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void type_missing() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setTitle("THis is a dummy title");
        postDto.setOwnerId("1009");
        String requestBody = objectMapper.writeValueAsString(postDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertOrUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("Post type is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void user_missing() throws Exception {
        PostDto postDto = new PostDto();
        postDto.setTitle("THis is a dummy title");
        postDto.setType(PostType2.VIDEO);
        String requestBody = objectMapper.writeValueAsString(postDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getInsertOrUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("User Id is missing", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    private String getInsertOrUpdateUri() {
        return "/" + restUriVersion + "/post/save";
    }
}