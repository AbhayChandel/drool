package com.hexlindia.drool.collection.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.collection.business.api.Collection;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.common.error.ErrorResult;
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

@WebMvcTest(value = CollectionRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class CollectionRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    Collection collection;

    @Test
    void addPost_missingPostId() throws Exception {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        String requestBody = objectMapper.writeValueAsString(collectionPostDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getAddPostUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("Post Id is empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    private String getAddPostUri() {
        return "/" + restUriVersion + "/collection/addpost";
    }
}