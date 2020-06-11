package com.hexlindia.drool.post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.dto.PostDto;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIT {

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
    void insert_article_post() throws JsonProcessingException {
        PostDto articlePost = new PostDto();
        articlePost.setTitle("This is an article");
        articlePost.setType(PostType2.ARTICLE);
        articlePost.setOwnerId("3");
        articlePost.setText("This is main content for test article");
        articlePost.setCoverPicture("abc.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(articlePost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        articlePost = responseEntity.getBody();
        assertNotNull(articlePost.getId());
        assertEquals("This is an article", articlePost.getTitle());
        assertEquals("0", articlePost.getLikes());
        assertEquals("0", articlePost.getViews());
        assertEquals(PostType2.ARTICLE, articlePost.getType());
        assertEquals("3", articlePost.getOwnerId());
        assertEquals("abc.jpg", articlePost.getCoverPicture());
        assertEquals("This is main content for test article", articlePost.getText());
    }

    @Test
    void update_article_post() throws JsonProcessingException {
        PostDto articlePost = new PostDto();
        articlePost.setId("102");
        articlePost.setTitle("Updated article title");
        articlePost.setType(PostType2.ARTICLE);
        articlePost.setOwnerId("3");
        articlePost.setText("Article description updated");
        articlePost.setCoverPicture("abc.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(articlePost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        articlePost = responseEntity.getBody();
        assertEquals("102", articlePost.getId());
        assertEquals("Updated article title", articlePost.getTitle());
        assertEquals("3.4k", articlePost.getLikes());
        assertEquals("456.7k", articlePost.getViews());
        assertEquals(PostType2.ARTICLE, articlePost.getType());
        assertEquals("3", articlePost.getOwnerId());
        assertEquals("abc.jpg", articlePost.getCoverPicture());
        assertEquals("Article description updated", articlePost.getText());
    }

    @Test
    void insert_video_post() throws JsonProcessingException {
        PostDto videoPost = new PostDto();
        videoPost.setTitle("This is a new video post");
        videoPost.setType(PostType2.VIDEO);
        videoPost.setOwnerId("1");
        videoPost.setSourceVideoId("axys34d");
        videoPost.setText("This is description for test video post");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(videoPost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        videoPost = responseEntity.getBody();
        assertNotNull(videoPost.getId());
        assertEquals("This is a new video post", videoPost.getTitle());
        assertEquals("0", videoPost.getLikes());
        assertEquals("0", videoPost.getViews());
        assertEquals(PostType2.VIDEO, videoPost.getType());
        assertEquals("1", videoPost.getOwnerId());
        assertEquals("axys34d", videoPost.getSourceVideoId());
        assertEquals("This is description for test video post", videoPost.getText());
    }

    @Test
    void update_video_post() throws JsonProcessingException {
        PostDto videoPost = new PostDto();
        videoPost.setId("101");
        videoPost.setTitle("This title has been updated");
        videoPost.setType(PostType2.VIDEO);
        videoPost.setOwnerId("2");
        videoPost.setSourceVideoId("axys34d");
        videoPost.setText("This is description for test video post");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(videoPost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        videoPost = responseEntity.getBody();
        assertEquals("101", videoPost.getId());
        assertEquals("This title has been updated", videoPost.getTitle());
        assertEquals("0", videoPost.getLikes());
        assertEquals("0", videoPost.getViews());
        assertEquals(PostType2.VIDEO, videoPost.getType());
        assertEquals("2", videoPost.getOwnerId());
        assertEquals("xsztiz", videoPost.getSourceVideoId());
        assertEquals("This is description for test video post", videoPost.getText());
    }

    @Test
    void insert_discussion_post() throws JsonProcessingException {
        PostDto discussionPost = new PostDto();
        discussionPost.setTitle("This is a discussion");
        discussionPost.setType(PostType2.DISCUSSION);
        discussionPost.setOwnerId("3");
        discussionPost.setText("This is discussion details");
        discussionPost.setCoverPicture("abc.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(discussionPost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        discussionPost = responseEntity.getBody();
        assertNotNull(discussionPost.getId());
        assertEquals("This is a discussion", discussionPost.getTitle());
        assertEquals("0", discussionPost.getLikes());
        assertEquals("0", discussionPost.getViews());
        assertEquals(PostType2.DISCUSSION, discussionPost.getType());
        assertEquals("3", discussionPost.getOwnerId());
        assertEquals("abc.jpg", discussionPost.getCoverPicture());
        assertEquals("This is discussion details", discussionPost.getText());
    }

    @Test
    void update_discussion_post() throws JsonProcessingException {
        PostDto discussionPost = new PostDto();
        discussionPost.setId("103");
        discussionPost.setTitle("[UPDATED]Which is the best body lotion for dry skin?");
        discussionPost.setType(PostType2.DISCUSSION);
        discussionPost.setOwnerId("1");
        discussionPost.setText("[UPDATED]I have shortlisted the following lotions");
        discussionPost.setCoverPicture("ABC.jpg");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(discussionPost), headers);
        ResponseEntity<PostDto> responseEntity = this.restTemplate.postForEntity(getInsertOrUpdateUri(), request, PostDto.class);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseEntity.getBody());
        discussionPost = responseEntity.getBody();
        assertEquals("103", discussionPost.getId());
        assertEquals("[UPDATED]Which is the best body lotion for dry skin?", discussionPost.getTitle());
        assertEquals("126", discussionPost.getLikes());
        assertEquals("12.3k", discussionPost.getViews());
        assertEquals(PostType2.DISCUSSION, discussionPost.getType());
        assertEquals("1", discussionPost.getOwnerId());
        assertEquals("ABC.jpg", discussionPost.getCoverPicture());
        assertEquals("[UPDATED]I have shortlisted the following lotions", discussionPost.getText());
    }


    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getInsertOrUpdateUri() {
        return "/" + restUriVersion + "/post/save";
    }
}
