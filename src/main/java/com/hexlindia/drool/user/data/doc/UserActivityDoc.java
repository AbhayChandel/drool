package com.hexlindia.drool.user.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user_activities")
public class UserActivityDoc {

    @Id
    private String id;
    private String userId;
    private UserLike userLike;

}
