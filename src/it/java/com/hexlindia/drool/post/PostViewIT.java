package com.hexlindia.drool.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hexlindia.drool.post.view.PostPageView;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.junit5.annotation.FlywayTestExtension;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FlywayTestExtension
@FlywayTest
public class PostViewIT {

    @Value("${rest.uri.version}")
    String restUriVersion;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void get_post_video() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<PostPageView> responseEntity = restTemplate.exchange(getPostUri() + "/101", HttpMethod.GET, httpEntity, PostPageView.class);
        PostPageView postPageView = responseEntity.getBody();

        assertNotNull(postPageView);
        assertEquals("101", postPageView.getId());
        assertEquals("video", postPageView.getType());
        assertEquals("Lakme 9to5 Lip Color", postPageView.getTitle());
        assertNotNull(postPageView.getDatePosted());
        assertEquals("0", postPageView.getLikes());
        assertEquals("0", postPageView.getViews());
        assertEquals("2", postPageView.getUsercardView().getId());
        assertEquals("priyankasingh", postPageView.getUsercardView().getUsername());
        assertEquals("This is aideo review for Lakme 9to5", postPageView.getText());
        assertEquals("xsztiz", postPageView.getSourceVideoId());
    }

    @Test
    void get_post_article() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<PostPageView> responseEntity = restTemplate.exchange(getPostUri() + "/102", HttpMethod.GET, httpEntity, PostPageView.class);
        PostPageView postPageView = responseEntity.getBody();

        assertNotNull(postPageView);
        assertEquals("102", postPageView.getId());
        assertEquals("article", postPageView.getType());
        assertEquals("How to choose the right shade", postPageView.getTitle());
        assertNotNull(postPageView.getDatePosted());
        assertEquals("3.4k", postPageView.getLikes());
        assertEquals("456.7k", postPageView.getViews());
        assertEquals("3", postPageView.getUsercardView().getId());
        assertEquals("sonam31", postPageView.getUsercardView().getUsername());
        assertEquals("This is an article about picking the right lip color shade", postPageView.getText());
        assertEquals("xsztiz.jpg", postPageView.getCoverPicture());
    }

    private String getPostUri() {
        return "/" + restUriVersion + "/view/post/page/id";
    }
}
