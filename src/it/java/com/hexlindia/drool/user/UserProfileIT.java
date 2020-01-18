package com.hexlindia.drool.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authToken;

    @BeforeEach
    private void getAuthenticationToken() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "talk_to_priyanka@gmail.com");
        jwtRequestJson.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        String response = this.restTemplate.postForEntity(getAuthenticationUri(), request, String.class).getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        authToken = rootNode.path("authToken").asText();
    }

    @Test
    void testFindingAvailableUsernameAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntity = restTemplate.exchange(getFindUsernameUri() + "/priya21", HttpMethod.GET, httpEntity, UserProfileTo.class);
        UserProfileTo userProfileToReturned = responseEntity.getBody();
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("priya21", userProfileToReturned.getUsername());
        assertEquals(8765432109L, userProfileToReturned.getMobile());
        assertEquals("Pune", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }

    @Test
    void testFindingProfileByIdAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntity = restTemplate.exchange(getUserProfileFindByIdUri() + "/2", HttpMethod.GET, httpEntity, UserProfileTo.class);
        UserProfileTo userProfileToReturned = responseEntity.getBody();
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("priya21", userProfileToReturned.getUsername());
        assertEquals(8765432109L, userProfileToReturned.getMobile());
        assertEquals("Pune", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }

    @Test
    void testFindingUnavailableUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindUsernameUri() + "/priya212", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User profile with username priya212 not found", responseEntity.getBody());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getUserProfileFindByIdUri() {
        return "/" + restUriVersion + "/user/profile/find/id";
    }

    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/accessall/user/profile/find/username";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/user/profile/update";
    }
}
