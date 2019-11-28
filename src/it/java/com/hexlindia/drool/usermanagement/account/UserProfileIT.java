package com.hexlindia.drool.usermanagement.account;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFindingAvailableUsernameAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
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
    void testFindingUnavailableUsername() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileTo> responseEntity = restTemplate.exchange(getFindUsernameUri() + "/priya212", HttpMethod.GET, httpEntity, UserProfileTo.class);
        UserProfileTo userProfileToReturned = responseEntity.getBody();
        assertEquals(0, userProfileToReturned.getId());
    }


    private String getFindUsernameUri() {
        return "/" + restUriVersion + "/user/profile/find/username";
    }

    private String getUpdateUri() {
        return "/" + restUriVersion + "/user/profile/update";
    }
}
