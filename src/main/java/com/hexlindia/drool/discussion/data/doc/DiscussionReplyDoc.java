package com.hexlindia.drool.discussion.data.doc;

import com.hexlindia.drool.common.data.doc.UserRef;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiscussionReplyDoc {

    public DiscussionReplyDoc() {
        this.id = new ObjectId();
    }

    @Id
    @Setter(AccessLevel.PROTECTED)
    private ObjectId id;

    private String reply;
    private UserRef userRef;
    private boolean active;
    private LocalDateTime datePosted;
    private int likes;
}
