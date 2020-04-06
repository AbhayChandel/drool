package com.hexlindia.drool.product.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.business.api.usecase.ProductSearch;
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

@WebMvcTest(value = ProductSearchRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class ProductSearchRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductSearch productSearch;

    private String getProductSearchByTagsdUri() {
        return "/" + restUriVersion + "/view/product/search/tags";
    }

    @Test
    public void searchByTags_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getProductSearchByTagsdUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void searchByTags_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getProductSearchByTagsdUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void searchByTags_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getProductSearchByTagsdUri() + "/Lakme kajal"));

        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.productSearch, times(1)).searchByTags(idArgumentCaptor.capture());
        assertEquals("Lakme kajal", idArgumentCaptor.getValue());
    }
}
