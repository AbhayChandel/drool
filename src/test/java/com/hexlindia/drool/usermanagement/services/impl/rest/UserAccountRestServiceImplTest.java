package com.hexlindia.drool.usermanagement.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.advice.ErrorResult;
import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserAccount;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserAccountRestServiceImplTest {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserAccount userAccount;

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
        when(this.userAccount.register(any())).thenReturn("");
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "kirti");
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<UserRegistrationDetailsTo> userRegistrationDetailsToArgumentCaptor = ArgumentCaptor.forClass(UserRegistrationDetailsTo.class);
        verify(userAccount, times(1)).register(userRegistrationDetailsToArgumentCaptor.capture());
        assertEquals("kirti22", userRegistrationDetailsToArgumentCaptor.getValue().getUsername());
        assertEquals("abc@gmail.com", userRegistrationDetailsToArgumentCaptor.getValue().getEmail());
        assertEquals("kirti", userRegistrationDetailsToArgumentCaptor.getValue().getPassword());
    }

    @Test
    void register_errorInRegisteringUser() throws Exception {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kirti22", "abc@gmail.com", "kriti");
        doThrow(new DataIntegrityViolationException("")).when(this.userAccount).register(any());
        String requestBody = objectMapper.writeValueAsString(userRegistrationDetailsTo);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is5xxServerError())
                .andReturn();

        assertEquals("Not able to register user at this time. Try again in some time.", mvcResult.getResponse().getContentAsString());
    }

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


    private String getRegisterUri() {
        return "/" + restUriVersion + "/user/account/register";
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/user/account/find/email";
    }


}