package com.hexlindia.drool.usermanagement.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRegistrationIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testUserRegistrationFailed() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userRegistrationDetails = new JSONObject();
        userRegistrationDetails.put("username", "priyanka99");
        userRegistrationDetails.put("email", "talk_to_priyanka@gmail.com");
        userRegistrationDetails.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(userRegistrationDetails.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/" + restUriVersion + "/user/register", request, String.class);
        assertEquals(500, responseEntity.getStatusCodeValue());
        assertEquals("Not able to register user at this time. Try again in some time.", responseEntity.getBody());
    }

    @Test
    void testUserRegisteredSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject userRegistrationDetails = new JSONObject();
        userRegistrationDetails.put("username", "filo72");
        userRegistrationDetails.put("email", "abhishek.gupta@gmail.com");
        userRegistrationDetails.put("password", "abhishek");
        HttpEntity<String> request = new HttpEntity<>(userRegistrationDetails.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/" + restUriVersion + "/user/register", request, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
    }
}
