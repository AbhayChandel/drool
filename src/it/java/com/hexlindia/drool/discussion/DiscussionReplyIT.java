package com.hexlindia.drool.discussion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.discussion.to.DiscussionReplyTo;
import com.hexlindia.drool.discussion.to.DiscussionTopicTo;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
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

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FlywayTestExtension
@FlywayTest
public class DiscussionReplyIT {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

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

    /*
    Discussion successfully created
     */
    @Test
    void testReplyPostedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicTo> discussionTopicresponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        int repliesBeforeNewReply = discussionTopicresponseEntity.getBody().getReplies();
        LocalDateTime discussionDateLastActiveBeforeNewReply = discussionTopicresponseEntity.getBody().getDateLastActive();

        JSONObject discussion = new JSONObject();
        discussion.put("discussionTopicId", "1");
        discussion.put("reply", "A new reply is posted");
        discussion.put("userId", "55");

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<DiscussionReplyTo> responseEntity = this.restTemplate.postForEntity(getPostUri(), request, DiscussionReplyTo.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionReplyTo discussionReplyToRetrieved = responseEntity.getBody();
        assertEquals("A new reply is posted", discussionReplyToRetrieved.getReply());
        assertEquals(55L, discussionReplyToRetrieved.getUserId());
        assertEquals(0, discussionReplyToRetrieved.getLikes());
        assertNotNull(discussionReplyToRetrieved.getDatePosted());

        discussionTopicresponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        assertEquals(repliesBeforeNewReply + 1, discussionTopicresponseEntity.getBody().getReplies());
        assertTrue(discussionTopicresponseEntity.getBody().getDateLastActive().isAfter(discussionDateLastActiveBeforeNewReply));

        responseEntity = restTemplate.exchange(getFindByIdUri() + "/" + discussionReplyToRetrieved.getId(), HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        discussionReplyToRetrieved = responseEntity.getBody();
        assertEquals(1L, discussionReplyToRetrieved.getDiscussionTopicId());
    }

    @Test
    void testReplyUdatedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);
        JSONObject discussion = new JSONObject();
        discussion.put("id", "1");
        discussion.put("reply", "No, Loreal is not better than Lakme");

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<DiscussionReplyTo> responseEntity = restTemplate.exchange(getUpdateUri(), HttpMethod.PUT, request, DiscussionReplyTo.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionReplyTo discussionReplyToReturned = responseEntity.getBody();
        assertEquals("No, Loreal is not better than Lakme", discussionReplyToReturned.getReply());
    }

    @Test
    void testFindById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionReplyTo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        DiscussionReplyTo discussionReplyTo = responseEntity.getBody();
        assertEquals(1L, discussionReplyTo.getId());
        assertEquals(3L, discussionReplyTo.getUserId());
        assertEquals(2, discussionReplyTo.getLikes());
    }

    @Test
    void testFindByIdUnavailableReplyTopic() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindByIdUri() + "/101", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("Reply search failed. Discussion reply with id 101 not found", responseEntity.getBody());
    }

    @Test
    void testIncrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionReplyTo> replyResponseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        int replyLikesBeforeIncrement = replyResponseEntity.getBody().getLikes();

        ResponseEntity<DiscussionTopicTo> discussionResponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        LocalDateTime discussionDateLastActiveBeforeLikeIncrement = discussionResponseEntity.getBody().getDateLastActive();

        JSONObject activityTo = new JSONObject();
        activityTo.put("postId", "1");
        activityTo.put("currentUserId", "5");
        HttpEntity<String> request = new HttpEntity<>(activityTo.toString(), headers);
        ResponseEntity<DiscussionReplyTo> responseEntityPut = restTemplate.exchange(getLikesIncrementUri(), HttpMethod.PUT, request, DiscussionReplyTo.class);
        assertEquals(200, responseEntityPut.getStatusCodeValue());

        replyResponseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        assertEquals(replyLikesBeforeIncrement + 1, replyResponseEntity.getBody().getLikes());

        discussionResponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        assertTrue(discussionResponseEntity.getBody().getDateLastActive().isAfter(discussionDateLastActiveBeforeLikeIncrement));
    }

    @Test
    void testDecrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionReplyTo> replyResponseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        int replyLikesBeforeIncrement = replyResponseEntity.getBody().getLikes();

        ResponseEntity<DiscussionTopicTo> discussionResponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        LocalDateTime discussionDateLastActiveBeforeLikeIncrement = discussionResponseEntity.getBody().getDateLastActive();

        JSONObject activityTo = new JSONObject();
        activityTo.put("postId", "1");
        activityTo.put("currentUserId", "5");
        HttpEntity<String> request = new HttpEntity<>(activityTo.toString(), headers);
        ResponseEntity<DiscussionReplyTo> responseEntityPut = restTemplate.exchange(getLikesDecrementUri(), HttpMethod.PUT, request, DiscussionReplyTo.class);
        assertEquals(200, responseEntityPut.getStatusCodeValue());

        replyResponseEntity = restTemplate.exchange(getFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionReplyTo.class);
        assertEquals(replyLikesBeforeIncrement - 1, replyResponseEntity.getBody().getLikes());

        discussionResponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicTo.class);
        assertTrue(discussionResponseEntity.getBody().getDateLastActive().isAfter(discussionDateLastActiveBeforeLikeIncrement));
    }

    @Test
    void testDeleteReply() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(null, headers);

        ResponseEntity<DiscussionTopicTo> discussionTopicresponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, request, DiscussionTopicTo.class);
        int repliesBeforeReplyDeletion = discussionTopicresponseEntity.getBody().getReplies();

        ResponseEntity<DiscussionReplyTo> responseEntityDelete = restTemplate.exchange(getDeleteByIdUri() + "/1", HttpMethod.DELETE, request, DiscussionReplyTo.class);
        assertEquals(200, responseEntityDelete.getStatusCodeValue());

        discussionTopicresponseEntity = restTemplate.exchange(getDiscussionTopicFindByIdUri() + "/1", HttpMethod.GET, request, DiscussionTopicTo.class);
        assertEquals(repliesBeforeReplyDeletion - 1, discussionTopicresponseEntity.getBody().getReplies());
    }


    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/user/account/authenticate";
    }

    private String getPostUri() {
        return "/" + restUriVersion + "/discussion/reply/post";
    }

    private String getDiscussionTopicFindByIdUri() {
        return "/" + restUriVersion + "/discussion/find/id";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/reply/update";
    }

    private String getDeleteByIdUri() {
        return "/" + restUriVersion + "/discussion/reply/delete/id";
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/discussion/reply/find/id";
    }

    private String getLikesIncrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/increment";
    }

    private String getLikesDecrementUri() {
        return "/" + restUriVersion + "/discussion/reply/likes/decrement";
    }
}
