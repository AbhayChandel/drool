package com.hexlindia.drool.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.video.dto.VideoDto;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class VideoIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

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
    void testVideoInsertedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject productRefDto1 = new JSONObject();
        productRefDto1.put("id", "p123");
        productRefDto1.put("name", "Tom Ford Vetiver");
        productRefDto1.put("type", "Fragrance");
        JSONObject productRefDto2 = new JSONObject();
        productRefDto2.put("id", "p456");
        productRefDto2.put("name", "Tom Ford Black");
        productRefDto2.put("type", "Fragrance");
        JSONArray productRefDtoList = new JSONArray();
        productRefDtoList.put(productRefDto1);
        productRefDtoList.put(productRefDto2);
        JSONObject UserRefDto = new JSONObject();
        UserRefDto.put("id", "u123");
        UserRefDto.put("username", "user123");
        JSONObject videoDoc = new JSONObject();
        videoDoc.put("type", "review");
        videoDoc.put("title", "Review for Tom Ford Vetiver");
        videoDoc.put("description", "This is an honest review of Tom Ford Vetiver");
        videoDoc.put("sourceId", "s123");
        videoDoc.put("productRefDtoList", productRefDtoList);
        videoDoc.put("userRefDto", UserRefDto);

        HttpEntity<String> request = new HttpEntity<>(videoDoc.toString(), headers);
        ResponseEntity<VideoDto> responseEntity = this.restTemplate.postForEntity(getInsertUri(), request, VideoDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        VideoDto videoDto = responseEntity.getBody();
        assertNotNull(videoDto.getId());
        assertEquals("review", videoDto.getType());
        assertEquals("Review for Tom Ford Vetiver", videoDto.getTitle());
        assertEquals("This is an honest review of Tom Ford Vetiver", videoDto.getDescription());
        assertEquals("s123", videoDto.getSourceId());
        assertEquals(2, videoDto.getProductRefDtoList().size());
        assertEquals("p123", videoDto.getProductRefDtoList().get(0).getId());
        assertEquals("Tom Ford Vetiver", videoDto.getProductRefDtoList().get(0).getName());
        assertEquals("Fragrance", videoDto.getProductRefDtoList().get(0).getType());
        assertEquals("u123", videoDto.getUserRefDto().getId());
        assertEquals("user123", videoDto.getUserRefDto().getUsername());
    }

    @Test
    void testVideoLikeInsertedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject videoLikeUnlikeDto = new JSONObject();
        videoLikeUnlikeDto.put("videoId", "5e4w87ce64787a51asdffsdf073d0915");
        videoLikeUnlikeDto.put("videoTitle", "KAY BEAUTY by Katrina Kaif Unbaised/Honest REVIEW | Anindita Chakravarty");
        videoLikeUnlikeDto.put("userId", "1");

        HttpEntity<String> request = new HttpEntity<>(videoLikeUnlikeDto.toString(), headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(getIncrementLikesUri(), HttpMethod.PUT, request, Boolean.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().booleanValue());
    }

    @Test
    void testVideoLikeInsertmultipleSave() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject videoLikeUnlikeDto = new JSONObject();
        videoLikeUnlikeDto.put("videoId", "5e487ce6478sdf7a51asdffg073d0915");
        videoLikeUnlikeDto.put("videoTitle", "KAY BEAUTY by Katrina Kaif Unbaised/Honest REVIEW | Anindita Chakravarty");
        videoLikeUnlikeDto.put("userId", "1");

        HttpEntity<String> request = new HttpEntity<>(videoLikeUnlikeDto.toString(), headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(getIncrementLikesUri(), HttpMethod.PUT, request, Boolean.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().booleanValue());

        responseEntity = restTemplate.exchange(getIncrementLikesUri(), HttpMethod.PUT, request, Boolean.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertFalse(responseEntity.getBody().booleanValue());
    }

    @Test
    void testVideoLikeRemovedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject videoLikeUnlikeDto = new JSONObject();
        videoLikeUnlikeDto.put("videoId", "5e487ce64787a51asdfsdfaf073d0915");
        videoLikeUnlikeDto.put("videoTitle", "KAY BEAUTY by Katrina Kaif Unbaised/Honest REVIEW | Anindita Chakravarty");
        videoLikeUnlikeDto.put("userId", "1");

        HttpEntity<String> request = new HttpEntity<>(videoLikeUnlikeDto.toString(), headers);
        restTemplate.exchange(getIncrementLikesUri(), HttpMethod.PUT, request, Boolean.class);

        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(getDecrementLikesUri(), HttpMethod.PUT, request, Boolean.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody().booleanValue());


    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getInsertUri() {
        return "/" + restUriVersion + "/video/insert";
    }

    private String getIncrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/increment";
    }

    private String getDecrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/decrement";
    }
}
