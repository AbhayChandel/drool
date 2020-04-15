package com.hexlindia.drool.discussion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.common.data.doc.UserRef;
import com.hexlindia.drool.common.data.mongo.MongoDataInsertion;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.data.doc.DiscussionReplyDoc;
import com.hexlindia.drool.discussion.data.doc.DiscussionTopicDoc;
import com.hexlindia.drool.discussion.dto.DiscussionReplyDto;
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

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({MongoDBConfig.class, MongoDataInsertion.class})
public class DiscussionReplyIT {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${rest.uri.version}")
    String restUriVersion;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    MongoDataInsertion mongoDataInsertion;

    private String authToken;
    private ObjectId insertedAccountId = null;

    private ObjectId insertDiscussionTopic;
    private ObjectId insertedReplyId;

    @BeforeEach
    private void getAuthenticationToken() throws JSONException, JsonProcessingException {
        insertedAccountId = mongoDataInsertion.insertUserData();


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
        DiscussionReplyDoc discussionReplyDoc = new DiscussionReplyDoc();
        discussionReplyDoc.setReply("As I told it is a great reply");
        discussionReplyDoc.setUserRef(new UserRef(userId, "shabana"));
        discussionReplyDoc.setActive(true);
        discussionReplyDoc.setLikes(190);
        discussionTopicDoc.setDiscussionReplyDocList(Arrays.asList(discussionReplyDoc, new DiscussionReplyDoc(), new DiscussionReplyDoc()));

        mongoOperations.save(discussionTopicDoc);
        insertDiscussionTopic = discussionTopicDoc.getId();
        insertedReplyId = discussionReplyDoc.getId();
    }

    /*
    Discussion successfully created
     */
    @Test
    void testReplyPostedSuccessfully() throws JSONException, JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        DiscussionReplyDto discussionReplyDto = new DiscussionReplyDto();
        discussionReplyDto.setDiscussionId(insertDiscussionTopic.toHexString());
        discussionReplyDto.setReply("This is a new reply");
        discussionReplyDto.setLikes("0");
        ObjectId userId = new ObjectId();
        discussionReplyDto.setUserRefDto(new UserRefDto(userId.toHexString(), "shabana"));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(discussionReplyDto), headers);
        ResponseEntity<DiscussionReplyDto> responseEntity = this.restTemplate.postForEntity(getPostUri(), request, DiscussionReplyDto.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        DiscussionReplyDto discussionReplyDtoRetrieved = responseEntity.getBody();
        assertNotNull(discussionReplyDtoRetrieved.getId());
        assertEquals("This is a new reply", discussionReplyDtoRetrieved.getReply());
        assertEquals(userId.toHexString(), discussionReplyDtoRetrieved.getUserRefDto().getId());
        assertEquals("shabana", discussionReplyDtoRetrieved.getUserRefDto().getUsername());
        assertEquals("0", discussionReplyDtoRetrieved.getLikes());
    }

    @Test
    void testReplyUpdatedSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);
        JSONObject parameters = new JSONObject();
        parameters.put("replyId", insertedReplyId.toHexString());
        parameters.put("discussionId", insertDiscussionTopic.toHexString());
        parameters.put("reply", "No, Loreal is not better than Lakme");

        HttpEntity<String> request = new HttpEntity<>(parameters.toString(), headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.exchange(getUpdateUri(), HttpMethod.PUT, request, Boolean.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody());

    }

    @Test
    void testIncrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        JSONObject parameters = new JSONObject();
        parameters.put("replyId", insertedReplyId.toHexString());
        parameters.put("discussionId", insertDiscussionTopic.toHexString());
        parameters.put("userId", ObjectId.get().toHexString());
        HttpEntity<String> httpEntity = new HttpEntity<>(parameters.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(getLikesIncrementUri(), HttpMethod.PUT, httpEntity, String.class);
        assertEquals(191, Integer.parseInt(response.getBody()));

    }

    @Test
    void testDecrementLikes() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        JSONObject parameters = new JSONObject();
        parameters.put("likes", 800);
        parameters.put("replyId", insertedReplyId.toHexString());
        parameters.put("discussionId", insertDiscussionTopic.toHexString());
        parameters.put("userId", ObjectId.get().toHexString());
        HttpEntity<String> httpEntity = new HttpEntity<>(parameters.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(getLikesDecrementUri(), HttpMethod.PUT, httpEntity, String.class);
        assertEquals(189, Integer.parseInt(response.getBody()));

    }

    @Test
    void testSetStatus() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(AUTHORIZATION_HEADER, BEARER_PREFIX + this.authToken);

        JSONObject parameters = new JSONObject();
        parameters.put("status", false);
        parameters.put("replyId", insertedReplyId.toHexString());
        parameters.put("discussionId", insertDiscussionTopic.toHexString());
        HttpEntity<String> httpEntity = new HttpEntity<>(parameters.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(getSetStatusUri(), HttpMethod.PUT, httpEntity, String.class);
        assertTrue(Boolean.valueOf(response.getBody()));

    }


    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
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

    private String getSetStatusUri() {
        return "/" + restUriVersion + "/discussion/reply/set";
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
