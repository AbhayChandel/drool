package com.hexlindia.drool.product.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.product.business.api.usecase.Product;
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
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ProductRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class ProductRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    Product productMocked;

    private String getAspectTemplatesUri() {
        return "/" + restUriVersion + "/product//aspecttemplates/id";
    }


    @Test
    public void getAspectTemplates_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getAspectTemplatesUri() + "/1"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void getAspectTemplates_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getAspectTemplatesUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAspectTemplates_ParametersPassedToBusinessLayer() throws Exception {
        ObjectId objectId = ObjectId.get();
        this.mockMvc.perform(MockMvcRequestBuilders.get(getAspectTemplatesUri() + "/" + objectId));

        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(this.productMocked, times(1)).getAspectTemplates(idArgumentCaptor.capture());
        assertEquals(objectId, idArgumentCaptor.getValue());
    }
}
