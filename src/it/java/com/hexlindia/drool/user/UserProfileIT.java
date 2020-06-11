package com.hexlindia.drool.user;

import com.hexlindia.drool.user.dto.ContributionSummaryDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserProfileIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testFindingProfileByIdAndValidateResponse() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<UserProfileDto> responseEntity = restTemplate.exchange(getUserProfileFindByIdUri() + "/1", HttpMethod.GET, httpEntity, UserProfileDto.class);
        UserProfileDto userProfileDtoReturned = responseEntity.getBody();
        assertEquals("1", userProfileDtoReturned.getId());
        assertEquals("Indore", userProfileDtoReturned.getCity());
        assertEquals("F", userProfileDtoReturned.getGender());
    }

    @Test
    @Disabled
    void testGettingContibutionSummary() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ContributionSummaryDto> responseEntity = restTemplate.exchange(getContributionSummaryUri() + "/2", HttpMethod.GET, httpEntity, ContributionSummaryDto.class);
        ContributionSummaryDto contributionSummaryDto = responseEntity.getBody();
        assertTrue(contributionSummaryDto.getVideoThumbnailDataDto().getTotalVideoCount() > 0);
        assertEquals("Review for Tom Ford Vetiver", contributionSummaryDto.getVideoThumbnailDataDto().getVideoThumbnailList().get(0).getTitle());
    }

    @Test
    @Disabled
    void testGettingContibutionSummaryNotFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<ContributionSummaryDto> responseEntity = restTemplate.exchange(getContributionSummaryUri() + "/10", HttpMethod.GET, httpEntity, ContributionSummaryDto.class);
        ContributionSummaryDto contributionSummaryDto = responseEntity.getBody();
        assertEquals(0, contributionSummaryDto.getVideoThumbnailDataDto().getTotalVideoCount());
        assertEquals(0, contributionSummaryDto.getVideoThumbnailDataDto().getVideoThumbnailList().size());
    }



    private String getAuthenticationUri() {
        return "/" + restUriVersion + "/accessall/user/account/authenticate";
    }

    private String getUserProfileFindByIdUri() {
        return "/" + restUriVersion + "/view/user/profile/find/id";
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

}
