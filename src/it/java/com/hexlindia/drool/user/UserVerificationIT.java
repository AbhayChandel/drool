package com.hexlindia.drool.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.constant.VerificationType;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserVerificationIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    private String authToken;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    private void setup() throws JSONException, JsonProcessingException {

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
    void findAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<UserVerificationDto[]> responseEntity = restTemplate.exchange(getFindAllUri() + "/1", HttpMethod.GET, httpEntity, UserVerificationDto[].class);
        List<UserVerificationDto> userVerificationDtoList = Arrays.asList(responseEntity.getBody());
        assertEquals(2, userVerificationDtoList.size());
        assertEquals("1", userVerificationDtoList.get(0).getUserId());
        assertEquals(VerificationType.email, userVerificationDtoList.get(0).getVerificationType());
        assertEquals("1", userVerificationDtoList.get(1).getUserId());
        assertEquals(VerificationType.mobile, userVerificationDtoList.get(1).getVerificationType());
    }

    @Test
    void findVerification() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<UserVerificationDto> responseEntity = restTemplate.exchange(getFindVerificationUri() + "/1/mobile", HttpMethod.GET, httpEntity, UserVerificationDto.class);
        UserVerificationDto userVerificationDto = responseEntity.getBody();
        assertNotNull(userVerificationDto);
        assertEquals("1", userVerificationDto.getUserId());
        assertEquals(VerificationType.mobile, userVerificationDto.getVerificationType());
    }

    private String getFindAllUri() {
        return "/" + restUriVersion + "/user/verification/find/all";
    }

    private String getFindVerificationUri() {
        return "/" + restUriVersion + "/user/verification/find";
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }


}
