package com.hexlindia.drool.user.services.impl.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.error.ErrorResult;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserAccountRestServiceImpl.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebSecurityConfigurer.class, JwtValidationFilter.class}),
        excludeAutoConfiguration = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
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
    void register_missingParameterEmail() throws Exception {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setPassword("password");
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(userAccountDto);
        String requestBody = objectMapper.writeValueAsString(userRegistrationDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();


        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userAccountDto.emailId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Email Id cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_invalidParameterEmail() throws Exception {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("kriti");
        userAccountDto.setPassword("password");
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(userAccountDto);
        String requestBody = objectMapper.writeValueAsString(userRegistrationDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userAccountDto.emailId", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Email Id is not correct", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_missingParameterPassword() throws Exception {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("kirit@gmail.com");
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(userAccountDto);
        String requestBody = objectMapper.writeValueAsString(userRegistrationDto);
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ErrorResult responseErrorResult = objectMapper.readValue(contentAsString, ErrorResult.class);
        assertEquals("userAccountDto.password", responseErrorResult.getFieldValidationErrors().get(0).getField());
        assertEquals("Password cannot be empty", responseErrorResult.getFieldValidationErrors().get(0).getErrorMessage());
    }

    @Test
    void register_allParametersValid() throws Exception {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("kirit@gmail.com");
        userAccountDto.setPassword("kriti");
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(userAccountDto);
        String requestBody = objectMapper.writeValueAsString(userRegistrationDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void register_ParametersArePassedToBusinessLayer() throws Exception {
        when(this.userAccount.register(any())).thenReturn(null);

        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("priya@gmail.com");
        userAccountDto.setPassword("priya");

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setCity("Chandigarh");
        userProfileDto.setGender("M");
        userProfileDto.setMobile("9876543210");
        userProfileDto.setName("Ajay Singh");
        userProfileDto.setUsername("Ajayboss");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(userAccountDto);
        userRegistrationDto.setUserProfileDto(userProfileDto);

        String requestBody = objectMapper.writeValueAsString(userRegistrationDto);
        this.mockMvc.perform(MockMvcRequestBuilders.post(getRegisterUri())
                .content(requestBody).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        ArgumentCaptor<UserRegistrationDto> userRegistrationDtoArgumentCaptor = ArgumentCaptor.forClass(UserRegistrationDto.class);
        verify(userAccount, times(1)).register(userRegistrationDtoArgumentCaptor.capture());
        assertEquals("priya@gmail.com", userRegistrationDtoArgumentCaptor.getValue().getUserAccountDto().getEmailId());
        assertEquals("priya", userRegistrationDtoArgumentCaptor.getValue().getUserAccountDto().getPassword());
        assertEquals("Chandigarh", userRegistrationDtoArgumentCaptor.getValue().getUserProfileDto().getCity());
        assertEquals("M", userRegistrationDtoArgumentCaptor.getValue().getUserProfileDto().getGender());
        assertEquals("9876543210", userRegistrationDtoArgumentCaptor.getValue().getUserProfileDto().getMobile());
        assertEquals("Ajay Singh", userRegistrationDtoArgumentCaptor.getValue().getUserProfileDto().getName());
        assertEquals("Ajayboss", userRegistrationDtoArgumentCaptor.getValue().getUserProfileDto().getUsername());

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
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("sonam99@gmail.com");
        when(this.userAccount.findByEmail("sonam99@gmail.com")).thenReturn(userAccountDto);
        this.mockMvc.perform(MockMvcRequestBuilders.get(getFindEmailUri() + "/sonam99@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emailId").value("sonam99@gmail.com"));
    }

    private String getRegisterUri() {
        return "/" + restUriVersion + "/accessall/user/account/register";
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/accessall/user/account/find/email";
    }
}