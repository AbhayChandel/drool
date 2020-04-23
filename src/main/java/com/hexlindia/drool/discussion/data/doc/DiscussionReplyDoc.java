package com.hexlindia.drool.discussion.data.doc;

import com.hexlindia.drool.user.data.doc.UserRef;
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
    private ObjectId id;

    private String reply;
    private UserRef userRef;
    private LocalDateTime datePosted;
    private int likes;
}
