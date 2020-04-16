package com.hexlindia.drool.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.config.MongoDBTestConfig;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(MongoDBTestConfig.class)
@ImportAutoConfiguration(TransactionAutoConfiguration.class)
class UserAccountIT {

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

    @BeforeEach
    void setUp() {
        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();

        UserAccountDoc userAccountDocSonam = new UserAccountDoc();
        userAccountDocSonam.setEmailId("sonam99@gmail.com");
        userAccountDocSonam.setPassword("sonam");
        userAccountDocSonam.setActive(true);
        mongoOperations.save(userAccountDocSonam);

        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(userAccountDocPriyanka.getId());
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Sonam Sharama");
        userProfileDoc.setUsername("SonamLove");
        mongoOperations.save(userProfileDoc);
    }

    // To-Do Log these error messages
    /*
    User registration fails because email already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateEmail() throws JSONException {
        String username = "Sonam_singh";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", "sonam99@gmail.com");
        userAccountDto.put("password", "sonam");

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("mobile", "9876543210");
        userProfileDto.put("name", "Sonam Singh");
        userProfileDto.put("username", username);

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("Email sonam99@gmail.com already exists", responseEntity.getBody());

        // No insertion is done in profiles collection if insertion into account fails
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntityUserProfile = restTemplate.exchange(getFindUsernameUri() + "/" + username, HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntityUserProfile.getStatusCodeValue());
        Assertions.assertEquals("User profile with username " + username + " not found", responseEntityUserProfile.getBody());


    }

    // To-Do Log these error messages
    /*
    User registration fails because username already exist
     */
    @Test
    void testUserRegistrationFailedDuplicateUsername() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String emailId = "sahil101@gmail.com";

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", emailId);
        userAccountDto.put("password", "sonam");

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("mobile", "9876543210");
        userProfileDto.put("name", "Sonam Singh");
        userProfileDto.put("username", "SonamLove");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(409, responseEntity.getStatusCodeValue());
        assertEquals("Username SonamLove already exists", responseEntity.getBody());
    }

    /*
    Registration is successful
     */
    @Test
    void testUserRegisteredSuccessfully() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        JSONObject userAccountDto = new JSONObject();
        userAccountDto.put("emailId", "priya@gmail.com");
        userAccountDto.put("password", "priya");

        JSONObject userProfileDto = new JSONObject();
        userProfileDto.put("city", "Chandigarh");
        userProfileDto.put("gender", "M");
        userProfileDto.put("mobile", "9876543210");
        userProfileDto.put("name", "Priya Sharma");
        userProfileDto.put("username", "Priya11");

        JSONObject userRegistrationDto = new JSONObject();
        userRegistrationDto.put("account", userAccountDto);
        userRegistrationDto.put("profile", userProfileDto);

        HttpEntity<String> request = new HttpEntity<>(userRegistrationDto.toString(), headers);
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(getRegisterUri(), request, String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());

        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountDto> responseEntityAccount = restTemplate.exchange(getFindEmailUri() + "/priya@gmail.com", HttpMethod.GET, httpEntity, UserAccountDto.class);
        assertEquals("priya@gmail.com", responseEntityAccount.getBody().getEmailId());

        ResponseEntity<UserProfileDto> responseEntityProfile = restTemplate.exchange(getUserProfileFindByUsernameUri() + "/Priya11", HttpMethod.GET, httpEntity, UserProfileDto.class);
        assertEquals("Priya11", responseEntityProfile.getBody().getUsername());
    }

    @Test
    void testFindingValidEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserAccountDto> responseEntity = restTemplate.exchange(getFindEmailUri() + "/sonam99@gmail.com", HttpMethod.GET, httpEntity, UserAccountDto.class);
        UserAccountDto userAccountDtoReturned = responseEntity.getBody();
        assertEquals("sonam99@gmail.com", userAccountDtoReturned.getEmailId());
    }

    @Test
    void testFindingUnavailableEmail() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(getFindEmailUri() + "/meenac@gmail.com", HttpMethod.GET, httpEntity, String.class);
        Assertions.assertEquals(404, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User Account with email meenac@gmail.com not found", responseEntity.getBody());
    }

    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getRegisterUri() {
        return "/" + restUriVersion + "/accessall/user/account/register";
    }

    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/accessall/user/profile/find/username";
    }

    private String getUserProfileFindByIdUri() {
        return "/" + restUriVersion + "/user/profile/find/id";
    }

    private String getFindEmailUri() {
        return "/" + restUriVersion + "/accessall/user/account/find/email";
    }

    private String getUserProfileFindByUsernameUri() {
        return "/" + restUriVersion + "/accessall/user/profile/find/username";
    }
}
