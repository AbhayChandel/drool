package com.hexlindia.drool.violation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.mongo.MongoDataInsertion;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.violation.data.doc.ViolationTemplateDoc;
import com.hexlindia.drool.violation.dto.ViolationReportDto;
import org.bson.types.ObjectId;
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

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({MongoDataInsertion.class})
public class ViolationIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    private String authToken;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    MongoDataInsertion mongoDataInsertion;

    private ObjectId insertedUserId;

    @BeforeEach
    private void setup() throws JSONException, JsonProcessingException {
        insertedUserId = mongoDataInsertion.insertUserData();

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

        insertViolationsTemplate();
    }

    @Test
    void testGetViolationsTemplate() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String[]> responseEntity = restTemplate.exchange(getVoilationsTemplateUri() + "/reply", HttpMethod.GET, httpEntity, String[].class);
        assertNotNull(responseEntity.getBody());
        List<String> violations = Arrays.asList(responseEntity.getBody());
        assertEquals(6, violations.size());
    }

    @Test
    void testSaveViolationReport() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + this.authToken);

        ViolationReportDto violationReportDto = new ViolationReportDto();
        violationReportDto.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        ObjectId postId = ObjectId.get();
        violationReportDto.setPost(new PostRefDto(postId.toHexString(), "This is an ordinary comment", PostType.comment, PostMedium.text, null));
        violationReportDto.setPostOwner(new UserRefDto(insertedUserId.toHexString(), "priyanka"));
        violationReportDto.setReportedBy(new UserRefDto(insertedUserId.toHexString(), "sonam99"));

        HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(violationReportDto), headers);
        ResponseEntity<Boolean> responseEntity = this.restTemplate.postForEntity(getViolationReportSaveUri(), request, Boolean.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertTrue(responseEntity.getBody());
    }

    void insertViolationsTemplate() {
        ViolationTemplateDoc violationTemplateDoc = new ViolationTemplateDoc();
        violationTemplateDoc.setPostType("reply");
        violationTemplateDoc.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Off-topic", "Advertisement for a product"));
        mongoOperations.save(violationTemplateDoc);
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getVoilationsTemplateUri() {
        return "/" + restUriVersion + "/violation/template";
    }

    private String getViolationReportSaveUri() {
        return "/" + restUriVersion + "/violation/report/save";
    }
}
