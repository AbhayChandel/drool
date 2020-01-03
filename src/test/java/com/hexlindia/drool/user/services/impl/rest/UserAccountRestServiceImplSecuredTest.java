package com.hexlindia.drool.user.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserAccountRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
class UserAccountRestServiceImplSecuredTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserAccount userAccount;

    @Test
    public void findByEmail_HttpMethodNotAllowedError() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getFindEmailUri() + "/sonam99@gmail.com"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void findByEmail_ParametersMissing() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getFindEmailUri() + "/"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByEmail_ParametersPassedToBusinessLayer() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindEmailUri() + "/sonam99@gmail.com"));

        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(this.userAccount, times(1)).findByEmail(emailArgumentCaptor.capture());
        assertEquals("sonam99@gmail.com", emailArgumentCaptor.getValue());
    }

    @Test
    void findByEmail_ValidateJsonResponse() throws Exception {
        UserAccountTo userAccountTo = new UserAccountTo();
        userAccountTo.setEmail("sonam99@gmail.com");
        when(this.userAccount.findByEmail("sonam99@gmail.com")).thenReturn(userAccountTo);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindEmailUri() + "/sonam99@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("sonam99@gmail.com"));
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/user/account/find/email";
    }
}