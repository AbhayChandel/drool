package com.hexlindia.drool.activity.dto.mapper;

import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FeedDocDtoMapperTest {

    @Autowired
    FeedDocDtoMapper feedDocDtoMapper;

    @Test
    void toDto() {

        FeedDoc feedDocLakmeFoundation = new FeedDoc();
        ObjectId postId = ObjectId.get();
        feedDocLakmeFoundation.setPostId(postId);
        feedDocLakmeFoundation.setPostType("guide");
        feedDocLakmeFoundation.setPostMedium("video");
        feedDocLakmeFoundation.setTitle(("How To Apply Lakme Perfecting Liquid Foundation || How I Make It Full Coverage"));
        feedDocLakmeFoundation.setSourceId("QW46ldTDiBY");
        LocalDateTime datePosted = LocalDateTime.now().minusHours(2);
        feedDocLakmeFoundation.setDatePosted(datePosted);
        feedDocLakmeFoundation.setLikes(34978);
        feedDocLakmeFoundation.setViews(4385654);
        feedDocLakmeFoundation.setComments(580);
        feedDocLakmeFoundation.setProductRefList(Arrays.asList(new ProductRef("1", "Lakme Foundation", "foundation")));
        ObjectId userId = ObjectId.get();
        feedDocLakmeFoundation.setUserRef(new UserRef(userId, "shabanastyle"));
        FeedDto feedDto = feedDocDtoMapper.toDto(feedDocLakmeFoundation);

        assertEquals(postId.toHexString(), feedDto.getPostId());
        assertEquals("guide", feedDto.getPostType());
        assertEquals("video", feedDto.getPostMedium());
        assertEquals("How To Apply Lakme Perfecting Liquid Foundation || How I Make It Full Coverage", feedDto.getTitle());
        assertEquals("QW46ldTDiBY", feedDto.getSourceId());
        assertEquals(MetaFieldValueFormatter.getDateInDayMonCommaYear(datePosted), feedDto.getDatePosted());
        assertEquals("34.9k", feedDto.getLikes());
        assertEquals("4.3M", feedDto.getViews());
        assertEquals("580", feedDto.getComments());
        assertEquals("1", feedDto.getProductRefDtoList().get(0).getId());
        assertEquals("Lakme Foundation", feedDto.getProductRefDtoList().get(0).getName());
        assertEquals("foundation", feedDto.getProductRefDtoList().get(0).getType());
        assertEquals(userId.toHexString(), feedDto.getUserRefDto().getId());
        assertEquals("shabanastyle", feedDto.getUserRefDto().getUsername());

    }
}