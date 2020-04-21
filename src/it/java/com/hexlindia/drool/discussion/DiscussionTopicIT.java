package com.hexlindia.drool.discussion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.mongo.MongoDataInsertion;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.dto.DiscussionTopicDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@Import({MongoDataInsertion.class})
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
    private ObjectId insertDiscussionTopic;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    MongoDataInsertion mongoDataInsertion;

    @BeforeEach
    private void getAuthenticationToken() throws JSONException, JsonProcessingException {
        mongoDataInsertion.insertUserData();

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

        insertDiscussionTopicDocs();
    }

    private void insertDiscussionTopicDocs() {
        DiscussionTopicDoc discussionTopicDoc = new DiscussionTopicDoc();
        discussionTopicDoc.setTitle("This a dummy discussion topic");
        ObjectId userId = ObjectId.get();
        discussionTopicDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionTopicDoc.setActive(true);
        discussionTopicDoc.setViews(1190);
        discussionTopicDoc.setLikes(500);
        LocalDateTime datePosted = LocalDateTime.now();
        discussionTopicDoc.setDatePosted(datePosted);
        discussionTopicDoc.setDateLastActive(datePosted);
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionReplyDoc.setLikes(190);
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        mongoOperations.save(discussionTopicDoc);
        insertDiscussionTopic = discussionTopicDoc.getId();
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
        discussion.put("title", "A new topic needs to be created");
        JSONObject user = new JSONObject();
        ObjectId userId = new ObjectId();
        user.put("id", userId.toHexString());
        user.put("username", "shabana");
        discussion.put("user", user);

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<DiscussionTopicDto> responseEntity = this.restTemplate.postForEntity(getCreateUri(), request, DiscussionTopicDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionTopicDto discussionTopicDtoRetrieved = responseEntity.getBody();
        assertTrue(discussionTopicDtoRetrieved.isActive());
        assertNotNull(discussionTopicDtoRetrieved.getId());
        assertEquals("A new topic needs to be created", discussionTopicDtoRetrieved.getTitle());
        assertEquals(userId.toHexString(), discussionTopicDtoRetrieved.getUserRefDto().getId());
        assertEquals("0", discussionTopicDtoRetrieved.getLikes());
        assertEquals("0", discussionTopicDtoRetrieved.getViews());
        assertEquals("0", discussionTopicDtoRetrieved.getReplies());
        assertNotNull(discussionTopicDtoRetrieved.getDatePosted());
        assertNotNull(discussionTopicDtoRetrieved.getDateLastActive());
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
        discussion.put("id", insertDiscussionTopic);
        discussion.put("title", "This topic needs to be updated");

        HttpEntity<String> request = new HttpEntity<>(discussion.toString(), headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(getUpdateUri(), HttpMethod.PUT, request, Boolean.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody());

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
        ResponseEntity<DiscussionTopicDto> responseEntity = restTemplate.exchange(getFindByIdUri() + "/" + insertDiscussionTopic, HttpMethod.GET, httpEntity, DiscussionTopicDto.class);
        DiscussionTopicDto discussionTopicDto = responseEntity.getBody();
        assertEquals(insertDiscussionTopic.toHexString(), discussionTopicDto.getId());
        assertEquals("This a dummy discussion topic", discussionTopicDto.getTitle());
        assertEquals("shabana", discussionTopicDto.getUserRefDto().getUsername());
        assertEquals("1.1k", discussionTopicDto.getViews());
        assertEquals("500", discussionTopicDto.getLikes());
        assertEquals("As I told it is a great reply", discussionTopicDto.getDiscussionReplyDtoList().get(0).getReply());
        assertEquals("190", discussionTopicDto.getDiscussionReplyDtoList().get(0).getLikes());
        assertEquals("3", discussionTopicDto.getReplies());

    }

    @Test
    void testIncrementViews() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        HttpEntity<String> request = new HttpEntity<>(insertDiscussionTopic.toHexString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(getViewsIncrementUri(), HttpMethod.PUT, request, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1.1k", response.getBody());
    }

    @Test
    void testIncrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        JSONObject parameters = new JSONObject();
        parameters.put("id", insertDiscussionTopic.toHexString());
        parameters.put("userId", "123");

        HttpEntity<String> request = new HttpEntity<>(parameters.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(getLikesIncrementUri(), HttpMethod.PUT, request, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("501", response.getBody());
    }

    @Test
    void testDecrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        JSONObject parameters = new JSONObject();
        parameters.put("id", insertDiscussionTopic.toHexString());
        parameters.put("userId", "123");

        HttpEntity<String> request = new HttpEntity<>(parameters.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(getLikesDecrementUri(), HttpMethod.PUT, request, String.class);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("499", response.getBody());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getCreateUri() {
        return "/" + restUriVersion + "/discussion/post";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/discussion/update";
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/view/discussion/find/id";
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
