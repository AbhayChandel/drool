package com.hexlindia.drool.video;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.mongo.MongoDataInsertion;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.video.data.doc.VideoComment;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoCommentDto;
import com.hexlindia.drool.video.dto.VideoDto;
import com.hexlindia.drool.video.dto.VideoLikeUnlikeDto;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Import({MongoDataInsertion.class})
public class VideoIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authToken;

    private VideoDoc insertedVideoDoc;

    @Autowired
    private MongoDataInsertion mongoDataInsertion;

    @Autowired
    private MongoOperations mongoOperations;

    @BeforeEach
    private void setup() throws JSONException, JsonProcessingException {
        ObjectId userId = mongoDataInsertion.insertUserData();
        insertedVideoDoc = mongoDataInsertion.inserVideoData(userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "priyanka.singh@gmail.com");
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
        ObjectId userId = new ObjectId();
        JSONObject UserRefDto = new JSONObject();
        UserRefDto.put("id", userId.toHexString());
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
        assertEquals(userId.toHexString(), videoDto.getUserRefDto().getId());
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
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), "username1"));
        videoCommentDto.setComment("This is a dummy test");
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntity = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        VideoCommentDto videoCommentDtoReturned = responseEntity.getBody();
        assertNotNull(videoCommentDtoReturned);
        assertNotNull(videoCommentDtoReturned.getId());
    }

    @Test
    void testVideoCommentUpdatedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), "username1"));
        videoCommentDto.setComment("This is an update for the comment");
        videoCommentDto.setId(insertedVideoDoc.getCommentList().get(0).getId().toHexString());
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntity = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);
        assertEquals("This is an update for the comment", videoCommentDto.getComment());

        VideoDoc videoDOc = mongoOperations.findOne(query(where("_id").is(insertedVideoDoc.getId()).andOperator(where("active").is(true))), VideoDoc.class);
        List<VideoComment> commentList = videoDOc.getCommentList();
        for (VideoComment videoComment : commentList) {
            if (videoComment.getId().equals(insertedVideoDoc.getCommentList().get(0).getId())) {
                assertEquals("This is an update for the comment", videoComment.getComment());
                return;
            }
        }
        fail("Video comment was not updated");


    }

    @Test
    void testVideoCommentDeletedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        VideoCommentDto videoCommentDto = new VideoCommentDto();
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), "username1"));
        videoCommentDto.setComment("This is a dummy test");
        HttpEntity<String> request = new HttpEntity<>(new ObjectMapper().writeValueAsString(videoCommentDto), headers);
        ResponseEntity<VideoCommentDto> responseEntity = restTemplate.exchange(getInsertCommentUri(), HttpMethod.PUT, request, VideoCommentDto.class);

        videoCommentDto = new VideoCommentDto();
        String commentId = responseEntity.getBody().getId();
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), null));
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
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), "This is a test post", "guide", "video", null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), "username1"));
        videoCommentDto.setComment("This is a dummy test");
        videoCommentDto.setLikes("9");
        videoCommentDto.setId(insertedVideoDoc.getCommentList().get(0).getId().toHexString());
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
        videoCommentDto.setPostRefDto(new PostRefDto(insertedVideoDoc.getId().toHexString(), null, null, null, null));
        videoCommentDto.setUserRefDto(new UserRefDto(ObjectId.get().toHexString(), null));
        videoCommentDto.setId(insertedVideoDoc.getCommentList().get(0).getId().toHexString());
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
        ObjectId userId = new ObjectId();
        JSONObject UserRefDto = new JSONObject();
        UserRefDto.put("id", userId.toHexString());
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
