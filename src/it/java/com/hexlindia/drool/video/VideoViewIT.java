package com.hexlindia.drool.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.video.view.VideoCardView;
import com.hexlindia.drool.video.view.VideoCommentCardView;
import com.hexlindia.drool.video.view.VideoPageView;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@FlywayTestExtension
@FlywayTest
public class VideoViewIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /*
    Find Video card view by video id
     */
    @Test
    void testFindVideoCardById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<VideoCardView> responseEntity = restTemplate.exchange(getfindVideoCardViewByIdUri() + "/1", HttpMethod.GET, httpEntity, VideoCardView.class);
        VideoCardView videoCardView = responseEntity.getBody();
        assertEquals("1", videoCardView.getVideoView().getVideoId());
        assertEquals("Reviewed Lakme 9to5 lipcolor", videoCardView.getVideoView().getTitle());
        assertEquals("1", videoCardView.getVideoView().getUserId());
        assertEquals("M7lc1UVf-VE", videoCardView.getVideoView().getSourceVideoId());
        assertEquals("I have tried to swatch all the shades of 9to5 lipcolor", videoCardView.getVideoView().getDescription());
        assertEquals("10", videoCardView.getVideoView().getLikes());
        assertEquals("200", videoCardView.getVideoView().getViews());
        assertEquals("2", videoCardView.getVideoView().getCommentCount());
        assertEquals("priyanka11", videoCardView.getUserProfileCardView().getUsername());
    }

    /*
    Find Video page view by video id
     */
    @Test
    void testFindDiscussionPageViewById() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<VideoPageView> responseEntity = restTemplate.exchange(getfindVideoPageViewByIdUri() + "/1", HttpMethod.GET, httpEntity, VideoPageView.class);
        VideoPageView videoPageView = responseEntity.getBody();

        VideoCardView videoCardView = videoPageView.getVideoCardView();
        assertEquals("1", videoCardView.getVideoView().getVideoId());
        assertEquals("Reviewed Lakme 9to5 lipcolor", videoCardView.getVideoView().getTitle());
        assertEquals("1", videoCardView.getVideoView().getUserId());
        assertEquals("M7lc1UVf-VE", videoCardView.getVideoView().getSourceVideoId());
        assertEquals("I have tried to swatch all the shades of 9to5 lipcolor", videoCardView.getVideoView().getDescription());
        assertEquals("10", videoCardView.getVideoView().getLikes());
        assertEquals("200", videoCardView.getVideoView().getViews());
        assertEquals("2", videoCardView.getVideoView().getCommentCount());
        assertEquals("priyanka11", videoCardView.getUserProfileCardView().getUsername());

        List<VideoCommentCardView> videoCommentCardViews = videoPageView.getVideoCommentCardViewList();
        assertEquals(2, videoCommentCardViews.size());
        VideoCommentCardView videoCommentCardView = videoCommentCardViews.get(0);
        assertEquals("1", videoCommentCardView.getVideoCommentView().getVideoCommentId());
        assertEquals("Great job. I really like all your videos", videoCommentCardView.getVideoCommentView().getComment());
        assertEquals("1", videoCommentCardView.getVideoCommentView().getVideoId());
        assertEquals("2", videoCommentCardView.getVideoCommentView().getUserId());
        assertEquals("2", videoCommentCardView.getVideoCommentView().getLikes());
        assertEquals("priya21", videoCommentCardView.getUserProfileCardView().getUsername());

    }

    private String getfindVideoCardViewByIdUri() {
        return "/" + restUriVersion + "/view/video/card/id";
    }

    private String getfindVideoPageViewByIdUri() {
        return "/" + restUriVersion + "/view/video/page/id";
    }
}
