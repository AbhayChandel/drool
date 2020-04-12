package com.hexlindia.drool.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.services.JwtResponse;
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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JwtSecurityIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    PasswordEncoder passwordEncoder;

    private ObjectId insertedDocId = null;

    @BeforeEach
    void setUp() {

        UserAccountDoc userAccountDocSonam = new UserAccountDoc();
        userAccountDocSonam.setEmailId("sonam99@gmail.com");
        userAccountDocSonam.setPassword(passwordEncoder.encode("sonam"));
        userAccountDocSonam.setActive(true);
        mongoOperations.save(userAccountDocSonam);
        insertedDocId = userAccountDocSonam.getId();

        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(userAccountDocSonam.getId());
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Sonam Sharama");
        userProfileDoc.setUsername("SonamLove");
        mongoOperations.save(userProfileDoc);
    }

    @Test
    void testTokenGenerationInvalidUsername() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "talk_to_priyankaa@gmail.com");
        jwtRequestJson.put("password", "priyanka");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getAuthenticationUri(), request, String.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
        assertEquals("Wrong Username or Password", responseEntity.getBody());
    }

    @Test
    void testTokenGenerationInvalidPassword() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "talk_to_priyanka@gmail.com");
        jwtRequestJson.put("password", "priyankaa");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getAuthenticationUri(), request, String.class);
        assertEquals(401, responseEntity.getStatusCodeValue());
        assertEquals("Wrong Username or Password", responseEntity.getBody());
    }

    @Test
    void testTokenGenerationValidCredentials() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jwtRequestJson = new JSONObject();
        jwtRequestJson.put("email", "sonam99@gmail.com");
        jwtRequestJson.put("password", "sonam");
        HttpEntity<String> request = new HttpEntity<>(jwtRequestJson.toString(), headers);
        ResponseEntity<JwtResponse> responseEntity = this.restTemplate.postForEntity(getAuthenticationUri(), request, JwtResponse.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        JwtResponse jwtResponse = responseEntity.getBody();
        assertNotNull(jwtResponse.getAuthToken());
        assertEquals(insertedDocId.toHexString(), jwtResponse.getAuthenticatedUserDetails().getAccountId());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }
}
