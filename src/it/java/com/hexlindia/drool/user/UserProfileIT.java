package com.hexlindia.drool.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.business.api.to.ContributionSummaryDto;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.video.dto.VideoDto;
import org.json.JSONArray;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileIT {

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

        headers.add("Authorization", "Bearer " + authToken);
        insertVideoData(headers);
    }

    @Test
    void testFindingAvailableUsernameAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntity = restTemplate.exchange(getFindUsernameUri() + "/priya21", HttpMethod.GET, httpEntity, UserProfileTo.class);
        UserProfileTo userProfileToReturned = responseEntity.getBody();
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("priya21", userProfileToReturned.getUsername());
        assertEquals(8765432109L, userProfileToReturned.getMobile());
        assertEquals("Pune", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }

    @Test
    void testFindingProfileByIdAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntity = restTemplate.exchange(getUserProfileFindByIdUri() + "/2", HttpMethod.GET, httpEntity, UserProfileTo.class);
        UserProfileTo userProfileToReturned = responseEntity.getBody();
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("priya21", userProfileToReturned.getUsername());
        assertEquals(8765432109L, userProfileToReturned.getMobile());
        assertEquals("Pune", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }

    @Test
    void testGettingContibutionSummary() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ContributionSummaryDto> responseEntity = restTemplate.exchange(getContributionSummaryUri() + "/u123", HttpMethod.GET, httpEntity, ContributionSummaryDto.class);
        ContributionSummaryDto contributionSummaryDto = responseEntity.getBody();
        assertTrue(contributionSummaryDto.getVideoThumbnailDataDto().getTotalVideoCount() > 0);
        assertEquals("Review for Tom Ford Vetiver", contributionSummaryDto.getVideoThumbnailDataDto().getVideoThumbnailList().get(0).getTitle());
    }

    @Test
    void testGettingContibutionSummaryNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ContributionSummaryDto> responseEntity = restTemplate.exchange(getContributionSummaryUri() + "/u1233", HttpMethod.GET, httpEntity, ContributionSummaryDto.class);
        ContributionSummaryDto contributionSummaryDto = responseEntity.getBody();
        assertEquals(0, contributionSummaryDto.getVideoThumbnailDataDto().getTotalVideoCount());
        assertEquals(0, contributionSummaryDto.getVideoThumbnailDataDto().getVideoThumbnailList().size());
    }

    @Test
    void testFindingUnavailableUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindUsernameUri() + "/priya212", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User profile with username priya212 not found", responseEntity.getBody());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getUserProfileFindByIdUri() {
        return "/" + restUriVersion + "/user/profile/find/id";
    }

    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/accessall/user/profile/find/username";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/user/profile/update";
    }

    private String getContributionSummaryUri() {
        return "/" + restUriVersion + "/view/profile/contributions/id";
    }

    private String getVideoInsertUri() {
        return "/" + restUriVersion + "/video/save";
    }

    private void insertVideoData(HttpHeaders headers) throws JSONException {
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
        videoDoc.put("active", true);

        HttpEntity<String> request = new HttpEntity<>(videoDoc.toString(), headers);
        ResponseEntity<VideoDto> responseEntity = this.restTemplate.postForEntity(getVideoInsertUri(), request, VideoDto.class);
    }
}
