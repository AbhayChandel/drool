package com.hexlindia.drool.violation.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.filters.JwtValidationFilter;
import com.hexlindia.drool.violation.business.api.Violation;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ViolationRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class ViolationRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    Violation violationMocked;

    @Test
    void getViolations_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getVoilationsTemplateUri() + "/remarks"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void post_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getVoilationsTemplateUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void saveViolationReport_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getViolationReportSaveUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void saveViolationReport_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getViolationReportSaveUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private String getVoilationsTemplateUri() {
        return "/" + restUriVersion + "/violation/template";
    }

    private String getViolationReportSaveUri() {
        return "/" + restUriVersion + "/violation/report/save";
    }
}