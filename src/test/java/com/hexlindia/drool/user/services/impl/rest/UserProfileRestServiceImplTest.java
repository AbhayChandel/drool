package com.hexlindia.drool.user.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
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
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserProfileRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class UserProfileRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserProfile userProfile;

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
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindByIdUri() + "/8"));
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(this.userProfile, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(8, idArgumentCaptor.getValue().longValue());
    }

    @Test
    void findById_ValidateJsonResponse() throws Exception {
        UserProfileTo userProfileTo = new UserProfileTo(2L, "priya21", 8765432109L, "Pune", 'F');
        when(this.userProfile.findById(2L)).thenReturn(userProfileTo);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindByIdUri() + "/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("priya21"))
                .andExpect(jsonPath("$.mobile").value(8765432109L))
                .andExpect(jsonPath("$.city").value("Pune"))
                .andExpect(jsonPath("$.gender").value("F"));
    }

    @Test
    public void findByUsername_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getFindUsernameUri() + "/priya21"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findByUsername_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getFindUsernameUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByUsername_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindUsernameUri() + "/priya21"));
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.userProfile, times(1)).findByUsername(usernameArgumentCaptor.capture());
        assertEquals("priya21", usernameArgumentCaptor.getValue());
    }

    @Test
    void findByUsername_ValidateJsonResponse() throws Exception {
        UserProfileTo userProfileTo = new UserProfileTo(2L, "priya21", 8765432109L, "Pune", 'F');
        when(this.userProfile.findByUsername("priya21")).thenReturn(userProfileTo);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindUsernameUri() + "/priya21"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("priya21"))
                .andExpect(jsonPath("$.mobile").value(8765432109L))
                .andExpect(jsonPath("$.city").value("Pune"))
                .andExpect(jsonPath("$.gender").value("F"));
    }

    @Test
    public void update_HttpMethodNotAllowedError() throws Exception {
        UserProfileTo userProfileTo = new UserProfileTo(2L, "priya21", 8765432109L, "Pune", 'F');
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
    void update_ValidateJsonResponse() throws Exception {
        UserProfileTo userProfileTo = new UserProfileTo(2L, "priya21", 8765432109L, "Pune", 'F');
        when(this.userProfile.update(any())).thenReturn(userProfileTo);
        String requestBody = objectMapper.writeValueAsString(userProfileTo);
        this.mockMvc.perform(MockMvcRequestBuilders.put(getUpdateUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.username").value("priya21"))
                .andExpect(jsonPath("$.mobile").value(8765432109L))
                .andExpect(jsonPath("$.city").value("Pune"))
                .andExpect(jsonPath("$.gender").value("F"));
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/user/profile/find/id";
    }

    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/accessall/user/profile/find/username";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/user/profile/update";
    }

}