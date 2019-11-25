package com.hexlindia.drool.usermanagement.security.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.security.services.JwtRequest;
import com.hexlindia.drool.usermanagement.security.services.api.rest.JwtUserAuthenticationRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtUserAuthenticationRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    JwtUserAuthenticationRestService jwtUserAuthenticationRestService;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    JwtUserAuthentication jwtUserAuthentication;

    @Autowired
    ObjectMapper objectMapper;


    @Test
    void createAuthenticationToken_RequestObjectMissing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/" + restUriVersion + "/user/authenticate"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createAuthenticationToken_InvalidCredentials() throws Exception {
        doThrow(new BadCredentialsException("Bad Credentials")).when(this.jwtUserAuthentication).authenticate(any(), any());
        JwtRequest jwtRequest = new JwtRequest();
        String requestBody = objectMapper.writeValueAsString(jwtRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/" + restUriVersion + "/user/authenticate")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Wrong Username or Password"));
    }


    @Test
    void createAuthenticationToken_validCredentials() throws Exception {
        when(this.jwtUserAuthentication.authenticate(any(), any())).thenReturn("dummyToken");
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail("shehnaz@gmail.com");
        jwtRequest.setPassword("shehnaz");
        String requestBody = objectMapper.writeValueAsString(jwtRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/" + restUriVersion + "/user/authenticate")
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}