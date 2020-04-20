package com.hexlindia.drool.discussion.data.doc;

import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "discussions")
public class DiscussionTopicDoc {

    @Id
    private ObjectId id;

    private String title;
    private UserRef userRef;
    private LocalDateTime datePosted;
    private LocalDateTime dateLastActive;
    private int views;
    private int likes;

    @Transient
    private int repliesCount;
    private boolean active;

    @Field(value = "replies")
    private List<DiscussionReplyDoc> discussionReplyDocList;
}
