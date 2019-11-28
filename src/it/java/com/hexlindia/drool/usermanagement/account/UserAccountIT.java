package com.hexlindia.drool.usermanagement.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAccountIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    // To-Do Log these error messages
    /*
    User registration fails because email already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateEmail() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userRegistrationDetails = new JSONObject();
        userRegistrationDetails.put("username", "priyanka99");
        userRegistrationDetails.put("email", "talk_to_priyanka@gmail.com");
        userRegistrationDetails.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(userRegistrationDetails.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
        assertEquals("Not able to register user at this time. Try again in some time.", responseEntity.getBody());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntityProile = restTemplate.exchange(getFindUsernameUri() + "/priyanka99", HttpMethod.GET, httpEntity, UserProfileTo.class);
        assertNull(responseEntityProile.getBody().getUsername());
    }

    // To-Do Log these error messages
    /*
    User registration fails because username already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateUsername() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userRegistrationDetails = new JSONObject();
        userRegistrationDetails.put("username", "priyanka11");
        userRegistrationDetails.put("email", "priyanka11@gmail.com");
        userRegistrationDetails.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(userRegistrationDetails.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
        assertEquals("Not able to register user at this time. Try again in some time.", responseEntity.getBody());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountTo> responseEntityAccount = restTemplate.exchange(getFindEmailUri() + "/priyanka11@gmail.com", HttpMethod.GET, httpEntity, UserAccountTo.class);
        assertNull(responseEntityAccount.getBody().getEmail());
    }

    /*
    Registration is successful
     */
    @Test
    void testUserRegisteredSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userRegistrationDetails = new JSONObject();
        userRegistrationDetails.put("username", "filo72");
        userRegistrationDetails.put("email", "abhishek.gupta@gmail.com");
        userRegistrationDetails.put("password", "abhishek");
        HttpEntity<String> request = new HttpEntity<>(userRegistrationDetails.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountTo> responseEntityAccount = restTemplate.exchange(getFindEmailUri() + "/abhishek.gupta@gmail.com", HttpMethod.GET, httpEntity, UserAccountTo.class);
        assertEquals("abhishek.gupta@gmail.com", responseEntityAccount.getBody().getEmail());

        ResponseEntity<UserProfileTo> responseEntityProile = restTemplate.exchange(getFindUsernameUri() + "/filo72", HttpMethod.GET, httpEntity, UserProfileTo.class);
        assertEquals("filo72", responseEntityProile.getBody().getUsername());


    }

    @Test
    void testFindingAvailableEmailAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountTo> responseEntity = restTemplate.exchange(getFindEmailUri() + "/talk_to_priyanka@gmail.com", HttpMethod.GET, httpEntity, UserAccountTo.class);
        UserAccountTo userAccountToReturned = responseEntity.getBody();
        assertEquals(1L, userAccountToReturned.getId());
        assertEquals("talk_to_priyanka@gmail.com", userAccountToReturned.getEmail());
    }

    @Test
    void testFindingUnavailableEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountTo> responseEntity = restTemplate.exchange(getFindEmailUri() + "/meenac@gmail.com", HttpMethod.GET, httpEntity, UserAccountTo.class);
        UserAccountTo userAccountToReturned = responseEntity.getBody();
        Assertions.assertEquals(0L, userAccountToReturned.getId());
    }

    private String getRegisterUri() {
        return "/" + restUriVersion + "/user/account/register";
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/user/account/find/email";
    }

    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/user/profile/find/username";
    }
}
