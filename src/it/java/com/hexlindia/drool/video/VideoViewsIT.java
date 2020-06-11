package com.hexlindia.drool.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class VideoViewsIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    MongoTemplate mongoTemplate;

    private ObjectId populatedVideoId = null;
    private ObjectId userId = new ObjectId();

    @BeforeEach
    public void setUp() {
        VideoDoc videoDoc = new VideoDoc(PostType.guide, "This video will be prepoulated for testing", "This video is inserted as part of testing with MongoDB", "vQ765gh",
                Arrays.asList(new ProductRef("abc", "Lakme 9to5 Lipcolor", "lipcolor"), new ProductRef("pqr", "Chambor", "kajal"), new ProductRef("xyz", "Tom Ford Vetiver", "fragrance")),
                new UserRef(userId, "shabana"));
        videoDoc.setActive(true);
        videoDoc.setDatePosted(LocalDateTime.now());
        populatedVideoId = this.mongoTemplate.insert(videoDoc).getId();
    }

    @Disabled
    @Test
    void testFindVideoById() throws JSONException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<VideoDtoMOngo> responseEntity = restTemplate.exchange(getFindByIdUri() + "/" + populatedVideoId, HttpMethod.GET, httpEntity, VideoDtoMOngo.class);
        VideoDtoMOngo videoDtoMOngo = responseEntity.getBody();
        assertNotNull(videoDtoMOngo);
        assertNotNull(videoDtoMOngo.getId());
        assertEquals(PostType.guide, videoDtoMOngo.getType());
        assertEquals("This video will be prepoulated for testing", videoDtoMOngo.getTitle());
        assertEquals("This video is inserted as part of testing with MongoDB", videoDtoMOngo.getDescription());
        assertEquals("vQ765gh", videoDtoMOngo.getSourceId());
        assertEquals(3, videoDtoMOngo.getProductRefDtoList().size());
        assertEquals("abc", videoDtoMOngo.getProductRefDtoList().get(0).getId());
        assertEquals("Lakme 9to5 Lipcolor", videoDtoMOngo.getProductRefDtoList().get(0).getName());
        assertEquals("lipcolor", videoDtoMOngo.getProductRefDtoList().get(0).getType());
        assertEquals(userId.toHexString(), videoDtoMOngo.getUserRefDto().getId());
        assertEquals("shabana", videoDtoMOngo.getUserRefDto().getUsername());
    }

    private String getFindByIdUri() {
        return "/" + restUriVersion + "/view/video/find/id";
    }
}
