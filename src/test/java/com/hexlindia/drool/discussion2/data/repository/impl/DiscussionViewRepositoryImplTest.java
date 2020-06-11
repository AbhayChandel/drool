package com.hexlindia.drool.discussion2.data.repository.impl;

import com.hexlindia.drool.discussion2.view.DiscussionPreview;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@Import(DiscussionViewRepositoryImpl.class)
class DiscussionViewRepositoryImplTest {

    @Autowired
    DiscussionViewRepositoryImpl discussionViewRepositoryp;

    @Test
    void getDiscussionPreviews() {
        List<DiscussionPreview> discussionPreviews = discussionViewRepositoryp.getDiscussionPreviews(Arrays.asList(40001, 40002));
        assertEquals(2, discussionPreviews.size());
        DiscussionPreview discussionPreview = null;
        for (DiscussionPreview discussionPreview1 : discussionPreviews) {
            if (discussionPreview1.getId().equalsIgnoreCase("40002")) {
                discussionPreview = discussionPreview1;
                break;
            }
        }
        assertNotNull(discussionPreview);
        assertEquals("40002", discussionPreview.getId());
        assertEquals("How can I get fairer skin", discussionPreview.getTitle());
        assertEquals("2", discussionPreview.getLikes());
        assertEquals("3", discussionPreview.getReplies());
        assertEquals("2", discussionPreview.getUserProfilePreview().getId());
        assertEquals("priyankasingh", discussionPreview.getUserProfilePreview().getUsername());
    }
}