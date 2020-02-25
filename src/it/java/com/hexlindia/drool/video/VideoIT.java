package com.hexlindia.drool.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
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

    private String videoInsertedId;

    private String videoCommentInsertedId;

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

        headers.add("Authorization", "Bearer " + authToken);

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

        request = new HttpEntity<>(videoDoc.toString(), headers);
        ResponseEntity<VideoDto> responseEntity = this.restTemplate.postForEntity(getInsertUri(), request, VideoDto.class);
        videoInsertedId = responseEntity.getBody().getId();

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntityVideoCommentDto = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);
        videoCommentInsertedId = responseEntityVideoCommentDto.getBody().getId();

        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        videoCommentDto.setLikes("9");
        videoCommentDto.setId(videoCommentInsertedId);
        request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        restTemplate.exchange(getSaveCommentLikeUri(), HttpMethod.PUT, request, String.class);
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

        HttpEntity<String> request = new HttpEntity<>(getVideoLikeUnlikeDto(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getIncrementLikesUri(), HttpMethod.PUT, request, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("600", responseEntity.getBody());
    }

    @Test
    void testVideoLikeRemovedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        HttpEntity<String> request = new HttpEntity<>(getVideoLikeUnlikeDto(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getDecrementLikesUri(), HttpMethod.PUT, request, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("598", responseEntity.getBody());
    }

    @Test
    void testVideoCommentInsertedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntity = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        VideoCommentDto videoCommentDtoReturned = responseEntity.getBody();
        assertNotNull(videoCommentDtoReturned);
        assertNotNull(videoCommentDtoReturned.getId());
    }

    @Test
    void testVideoCommentDeletedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntity = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);

        videoCommentDto = new VideoCommentDto();
        String commentId = responseEntity.getBody().getId();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setId(commentId);

        request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<Boolean> responseEntityDelete = restTemplate.exchange(getDeleteCommentUri(), HttpMethod.PUT, request, Boolean.class);
        assertEquals(200, responseEntityDelete.getStatusCodeValue());
        assertTrue(responseEntityDelete.getBody().booleanValue());
    }

    @Test
    void testVideoCommentLiked() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", "username1"));
        videoCommentDto.setComment("This is a dummy test");
        videoCommentDto.setLikes("9");
        videoCommentDto.setId(videoCommentInsertedId);
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getSaveCommentLikeUri(), HttpMethod.PUT, request, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("10", responseEntity.getBody());
    }

    @Test
    void testVideoCommentLikeDelete() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(videoInsertedId, null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto("u123", null));
        videoCommentDto.setId(videoCommentInsertedId);
        videoCommentDto.setLikes("10");
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getDeleteCommentLikeUri(), HttpMethod.PUT, request, String.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("9", responseEntity.getBody());
    }

    private String getVideoLikeUnlikeDto() throws JSONException {
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
        videoDoc.put("likes", "599");

        HttpEntity<String> request = new HttpEntity<>(videoDoc.toString(), headers);
        ResponseEntity<VideoDto> responseEntity = this.restTemplate.postForEntity(getInsertUri(), request, VideoDto.class);


        VideoDto videoDto = responseEntity.getBody();
        VideoLikeUnlikeDto videoLikeUnlikeDto = new VideoLikeUnlikeDto();
        videoLikeUnlikeDto.setVideoId(videoDto.getId());
        videoLikeUnlikeDto.setVideoTitle(videoDto.getTitle());
        videoLikeUnlikeDto.setUserId(videoDto.getUserRefDto().getId());
        try {
            return new ObjectMapper().writeValueAsString(videoLikeUnlikeDto);
        } catch (JsonProcessingException e) {
            log.error("Unable to convert VideoDto object to String: " + e);
            return "";
        }
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getInsertUri() {
        return "/" + restUriVersion + "/video/save";
    }

    private String getIncrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/increment";
    }

    private String getDecrementLikesUri() {
        return "/" + restUriVersion + "/video/likes/decrement";
    }

    private String getInsertCommentUri() {
        return "/" + restUriVersion + "/video/insert/comment";
    }

    private String getDeleteCommentUri() {
        return "/" + restUriVersion + "/video/delete/comment";
    }

    private String getSaveCommentLikeUri() {
        return "/" + restUriVersion + "/video/comment/likes/increment";
    }

    private String getDeleteCommentLikeUri() {
        return "/" + restUriVersion + "/video/comment/likes/decrement";
    }
}
