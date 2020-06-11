package com.hexlindia.drool.feed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.feed.view.FeedItemPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FeedIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void get_feed() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<FeedItemPreview[]> responseEntity = restTemplate.exchange(getFeedUri() + "?pno=0&psize=3", HttpMethod.GET, httpEntity, FeedItemPreview[].class);

        assertNotNull(responseEntity.getBody());
        List<FeedItemPreview> feedItemPreviewList = Arrays.asList(responseEntity.getBody());
        assertEquals(3, feedItemPreviewList.size());

        boolean isFirstPreview = true;
        FeedItemPreview previousFeedItem = null;
        for (FeedItemPreview feedItemPreview : feedItemPreviewList) {
            if (isFirstPreview) {
                previousFeedItem = feedItemPreview;
                isFirstPreview = false;
                continue;
            }
            assertTrue(feedItemPreview.getDatePosted().isBefore(previousFeedItem.getDatePosted()));
            previousFeedItem = feedItemPreview;
        }
    }

    String getFeedUri() {
        return "/" + restUriVersion + "/view/feed";
    }
}
