package com.hexlindia.drool.discussion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import com.hexlindia.drool.discussion.to.DiscussionTopicActivityTo;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import lombok.extern.slf4j.Slf4j;
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

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class DiscussionTopicIT {

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
        authToken = rootNode.path("token").asText();
    }

    /*
    Discussion successfully created
     */
    @Test
    void testDiscussionPostedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject discussion = new JSONObject();
        discussion.put("topic", "A new topic needs to be created");
        discussion.put("userId", "55");

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = this.restTemplate.postForEntity(getCreateUri(), request, DiscussionTopicTo.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionTopicTo discussionTopicToRetrieved = responseEntity.getBody();
        assertEquals("A new topic needs to be created", discussionTopicToRetrieved.getTopic());
        assertEquals(55L, discussionTopicToRetrieved.getUserId());
        assertEquals(0, discussionTopicToRetrieved.getDiscussionTopicActivityTo().getLikes());
        assertEquals(0, discussionTopicToRetrieved.getDiscussionTopicActivityTo().getViews());
        assertEquals(0, discussionTopicToRetrieved.getDiscussionTopicActivityTo().getReplies());
        assertNotNull(discussionTopicToRetrieved.getDiscussionTopicActivityTo().getDatePosted());
        assertNotNull(discussionTopicToRetrieved.getDiscussionTopicActivityTo().getDateLastActive());
    }


    /*
    Discussion successfully updated
     */
    @Test
    void testDiscussionUpdatedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        JSONObject discussion = new JSONObject();
        discussion.put("id", "1");
        discussion.put("topic", "This topic needs to be updated");
        discussion.put("userId", "55");

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = restTemplate.exchange(getUpdateUri(), HttpMethod.PUT, request, DiscussionTopicTo.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionTopicTo discussionTopicToReturned = responseEntity.getBody();
        assertEquals("This topic needs to be updated", discussionTopicToReturned.getTopic());
        assertNotNull(discussionTopicToReturned.getDiscussionTopicActivityTo());

    }

    /*
    Find by Id
     */
    @Test
    void testFindById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        DiscussionTopicTo discussionTopicTo = responseEntity.getBody();
        assertEquals(1L, discussionTopicTo.getId());
        assertEquals(5L, discussionTopicTo.getUserId());

        DiscussionTopicActivityTo discussionTopicActivityTo = discussionTopicTo.getDiscussionTopicActivityTo();
        assertEquals(15, discussionTopicActivityTo.getViews());
        assertEquals(12, discussionTopicActivityTo.getLikes());
        assertEquals(1, discussionTopicActivityTo.getReplies());

        List<DiscussionReplyTo> discussionReplyToList = discussionTopicTo.getDiscussionReplyToList();
        assertTrue(discussionReplyToList.size() >= 1);
    }

    @Test
    void testFindById_UnavailableDiscussionTopic() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindByIdUri() + "/101", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("Topic search failed. Discussion topic with id 101 not found", responseEntity.getBody());
    }

    @Test
    void testIncrementViews() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        HttpEntity<String> request = new HttpEntity<>("1", headers);
        ResponseEntity responseEntityPut = restTemplate.exchange(getViewsIncrementUri(), HttpMethod.PUT, request, DiscussionTopicTo.class);
        assertEquals(200, responseEntityPut.getStatusCodeValue());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        DiscussionTopicTo discussionTopicTo = responseEntity.getBody();
        assertEquals(1L, discussionTopicTo.getId());

        DiscussionTopicActivityTo discussionTopicActivityTo = discussionTopicTo.getDiscussionTopicActivityTo();
        assertEquals(16, discussionTopicActivityTo.getViews());
    }

    @Test
    void testIncrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        int likesBeforeIncrement = responseEntity.getBody().getDiscussionTopicActivityTo().getLikes();
        Timestamp dateLastActiveBeforeIncrement = responseEntity.getBody().getDiscussionTopicActivityTo().getDateLastActive();

        JSONObject activityTo = new JSONObject();
        activityTo.put("postId", "1");
        activityTo.put("currentUserId", "5");
        HttpEntity<String> request = new HttpEntity<>(activityTo.toString(), headers);
        ResponseEntity responseEntityPut = restTemplate.exchange(getLikesIncrementUri(), HttpMethod.PUT, request, DiscussionTopicTo.class);
        assertEquals(200, responseEntityPut.getStatusCodeValue());

        responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        assertEquals(likesBeforeIncrement + 1, responseEntity.getBody().getDiscussionTopicActivityTo().getLikes());
        assertTrue(responseEntity.getBody().getDiscussionTopicActivityTo().getDateLastActive().getTime() > dateLastActiveBeforeIncrement.getTime());

    }

    @Test
    void testDecrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicTo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        int likesBeforeDecrement = responseEntity.getBody().getDiscussionTopicActivityTo().getLikes();
        Timestamp dateLastActiveBeforeDecrement = responseEntity.getBody().getDiscussionTopicActivityTo().getDateLastActive();

        JSONObject activityTo = new JSONObject();
        activityTo.put("postId", "1");
        activityTo.put("currentUserId", "5");
        HttpEntity<String> request = new HttpEntity<>(activityTo.toString(), headers);
        ResponseEntity responseEntityPut = restTemplate.exchange(getLikesDecrementUri(), HttpMethod.PUT, request, DiscussionTopicTo.class);
        assertEquals(200, responseEntityPut.getStatusCodeValue());

        responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        assertEquals(likesBeforeDecrement - 1, responseEntity.getBody().getDiscussionTopicActivityTo().getLikes());
        assertTrue(responseEntity.getBody().getDiscussionTopicActivityTo().getDateLastActive().getTime() > dateLastActiveBeforeDecrement.getTime());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/user/account/authenticate";
    }

    private String getCreateUri() {
        return "/" + restUriVersion + "/discussion/create";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/update";
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/discussion/find/id";
    }

    private String getViewsIncrementUri() {
        return "/" + restUriVersion + "/discussion/views/increment";
    }

    private String getLikesIncrementUri() {
        return "/" + restUriVersion + "/discussion/likes/increment";
    }

    private String getLikesDecrementUri() {
        return "/" + restUriVersion + "/discussion/likes/decrement";
    }
}
