package com.hexlindia.drool.collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.common.data.constant.Visibility;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CollectionIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authToken;

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
    void add_post_and_add_new_collection() throws JsonProcessingException {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setName("Street Style");
        collectionPostDto.setAbout("All about street fashion");
        collectionPostDto.setVisibility(Visibility.PUBLIC);
        collectionPostDto.setOwnerId("1");
        collectionPostDto.setPostId("101");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(collectionPostDto), headers);
        ResponseEntity<Boolean> responseEntity = this.restTemplate.postForEntity(getAddPostUri(), request, Boolean.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody());
    }

    @Test
    void add_post_to_existing_collection() throws JsonProcessingException {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setCollectionId("1001");
        collectionPostDto.setPostId("101");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(collectionPostDto), headers);
        ResponseEntity<Boolean> responseEntity = this.restTemplate.postForEntity(getAddPostUri(), request, Boolean.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getAddPostUri() {
        return "/" + restUriVersion + "/collection/addpost";
    }
}
