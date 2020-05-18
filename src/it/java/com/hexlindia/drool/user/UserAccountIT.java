package com.hexlindia.drool.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import com.hexlindia.drool.user.services.JwtResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAccountIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    // To-Do Log these error messages
    /*
    User registration fails because email already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateEmail() throws JSONException {
        String username = "Sonam_singh";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", "sonam99@gmail.com");
        userAccountDto.put("password", "sonam");
        userAccountDto.put("username", username);

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("name", "Sonam Singh");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("Email already registered", responseEntity.getBody());
    }

    // To-Do Log these error messages
    /*
    User registration fails because username already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateUsername() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String emailId = "sahil101@gmail.com";
        String username = "sonam31";

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", emailId);
        userAccountDto.put("password", "sonam");
        userAccountDto.put("username", username);

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("name", "Sonam Singh");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("Username already registered", responseEntity.getBody());
    }

    @Test
    void testUserRegistrationFailedDuplicateMobile() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String emailId = "sahil101@gmail.com";
        String username = "sonam45";

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", emailId);
        userAccountDto.put("password", "sonam");
        userAccountDto.put("username", username);
        userAccountDto.put("mobile", "9876543210");

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("name", "Sonam Singh");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("Mobile already registered", responseEntity.getBody());
    }

    /*
    Registration is successful
     */
    @Test
    void testUserRegisteredSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", "priya@gmail.com");
        userAccountDto.put("password", "priya");
        userAccountDto.put("username", "Priya11");

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("name", "Priya Sharma");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<JwtResponse> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, JwtResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountDto> responseEntityAccount = restTemplate.exchange(getFindEmailUri() + "/priya@gmail.com", HttpMethod.GET, httpEntity, UserAccountDto.class);
        assertEquals("priya@gmail.com", responseEntityAccount.getBody().getEmailId());

        JwtResponse jwtResponse = responseEntity.getBody();
        headers.add("Authorization", "Bearer " + getAuthToken());
        httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserVerificationDto[]> responseEntityVerification = restTemplate.exchange(getFindAllVerificationUri() + "/" + jwtResponse.getAuthenticatedUserDetails().getAccountId(), HttpMethod.GET, httpEntity, UserVerificationDto[].class);
        assertEquals(2, responseEntityVerification.getBody().length);
    }

    @Test
    void testFindingValidEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountDto> responseEntity = restTemplate.exchange(getFindEmailUri() + "/sonam99@gmail.com", HttpMethod.GET, httpEntity, UserAccountDto.class);
        UserAccountDto userAccountDtoReturned = responseEntity.getBody();
        assertEquals("sonam99@gmail.com", userAccountDtoReturned.getEmailId());
    }

    @Test
    void testFindingUnavailableEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindEmailUri() + "/meenac@gmail.com", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User Account with Email/Username meenac@gmail.com not found", responseEntity.getBody());
    }

    private String getRegisterUri() {
        return "/" + restUriVersion + "/accessall/user/account/register";
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/accessall/user/account/find";
    }

    private String getFindAllVerificationUri() {
        return "/" + restUriVersion + "/user/verification/find/all";
    }

    private String getAuthToken() throws JsonProcessingException, JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "talk_to_priyanka@gmail.com");
        jwtRequestJson.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        String response = this.restTemplate.postForEntity("/" + restUriVersion + "/accessall/user/account/authenticate", request, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        return rootNode.path("authToken").asText();
    }
}