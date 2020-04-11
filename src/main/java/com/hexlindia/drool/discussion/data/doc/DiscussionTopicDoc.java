package com.hexlindia.drool.discussion.data.doc;

import com.hexlindia.drool.common.data.doc.UserRef;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "discussions")
public class DiscussionTopicDoc {

    @Id
    @Setter(AccessLevel.PROTECTED)
    private ObjectId id;

    private String title;
    private UserRef userRef;
    private LocalDateTime datePosted;
    private LocalDateTime dateLastActive;
    private int views;
    private int likes;
    private int repliesCount;
    private boolean active;

    @Field(value = "replies")
    private List<DiscussionReplyDoc> discussionReplyDocList;
}
