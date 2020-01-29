package com.hexlindia.drool.discussion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.discussion.view.DiscussionPageView;
import com.hexlindia.drool.discussion.view.DiscussionReplyCardView;
import com.hexlindia.drool.discussion.view.DiscussionTopicCardView;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FlywayTestExtension
@FlywayTest
public class DiscussionViewIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*
    Find Discussion topic card by Id
     */
    @Test
    void testFindDiscussionTopicCardById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionTopicCardView> responseEntity = restTemplate.exchange(getfindDiscussionTopicCardViewByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionTopicCardView.class);
        DiscussionTopicCardView discussionTopicCardView = responseEntity.getBody();
        assertEquals("1", discussionTopicCardView.getDiscussionTopicView().getTopicId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getTopic());
        assertEquals("1", discussionTopicCardView.getDiscussionTopicView().getUserId());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDatePosted());
        assertNotNull(discussionTopicCardView.getDiscussionTopicView().getDateLastActive());
        assertEquals("154.5k", discussionTopicCardView.getDiscussionTopicView().getViews());
        assertEquals("12.7k", discussionTopicCardView.getDiscussionTopicView().getLikes());
        assertEquals("234", discussionTopicCardView.getDiscussionTopicView().getReplies());
        assertEquals("1", discussionTopicCardView.getUserProfileCardView().getUserId());
        assertEquals("priyanka11", discussionTopicCardView.getUserProfileCardView().getUsername());
    }

    /*
    Find Discussion page by discussion topic id
     */
    @Test
    void testFindDiscussionPageViewById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<DiscussionPageView> responseEntity = restTemplate.exchange(getfindDiscussionPageViewByIdUri() + "/1", HttpMethod.GET, httpEntity, DiscussionPageView.class);
        DiscussionPageView discussionPageView = responseEntity.getBody();
        assertEquals(2, discussionPageView.getDiscussionReplyCardViewList().size());
        DiscussionReplyCardView discussionReplyCardView = discussionPageView.getDiscussionReplyCardViewList().get(0);
        assertEquals("1", discussionReplyCardView.getDiscussionReplyView().getReplyId());
        assertEquals("1", discussionReplyCardView.getDiscussionReplyView().getDiscussionTopicId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getReply());
        assertEquals("3", discussionReplyCardView.getDiscussionReplyView().getUserId());
        assertNotNull(discussionReplyCardView.getDiscussionReplyView().getDatePosted());
        assertEquals("2.4k", discussionReplyCardView.getDiscussionReplyView().getLikes());
        assertEquals("3", discussionReplyCardView.getUserProfileCardView().getUserId());
        assertEquals("sonam31", discussionReplyCardView.getUserProfileCardView().getUsername());
    }

    private String getfindDiscussionTopicCardViewByIdUri() {
        return "/" + restUriVersion + "/view/discussion/topic/id";
    }

    private String getfindDiscussionPageViewByIdUri() {
        return "/" + restUriVersion + "/view/discussion/page/id";
    }
}
