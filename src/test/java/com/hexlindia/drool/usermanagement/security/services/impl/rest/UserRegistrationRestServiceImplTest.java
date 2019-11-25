package com.hexlindia.drool.usermanagement.security.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.advice.ErrorResult;
import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.UserRegistration;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserRegistrationRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserRegistration userRegistration;

    @Test
    void register_HttpMethodNotAllowed() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.put(getRegisterUri()))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    void register_missingRequestObject() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void register_missingParameterUsername() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("", "kirti.gupta@gmial.com", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("username", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Username cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_missingParameterEmail() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("email", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Email cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_invalidParameterEmail() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("email", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Email is not correct", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_missingParameterPassword() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("password", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Password cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_allParametersValid() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void register_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.userRegistration.register(any())).thenReturn("");
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<UserRegistrationDetailsTo> userRegistrationDetailsToArgumentCaptor = ArgumentCaptor.forClass(UserRegistrationDetailsTo.class);
        verify(userRegistration, times(1)).register(userRegistrationDetailsToArgumentCaptor.capture());
        assertEquals("kirti22", userRegistrationDetailsToArgumentCaptor.getValue().getUsername());
        assertEquals("abc@gmail.com", userRegistrationDetailsToArgumentCaptor.getValue().getEmail());
        assertEquals("kirti", userRegistrationDetailsToArgumentCaptor.getValue().getPassword());
    }

    @Test
    void register_errorInRegisteringUser() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "kriti");
        doThrow(new DataIntegrityViolationException("")).when(this.userRegistration).register(any());
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertEquals("Not able to register user at this time. Try again in some time.", mvcResult.getResponse().getContentAsString());
    }


    private String getRegisterUri() {
        return "/" + restUriVersion + "/user/register";
    }


}