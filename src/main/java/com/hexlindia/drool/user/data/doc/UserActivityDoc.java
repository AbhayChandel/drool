package com.hexlindia.drool.user.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user_activities")
public class UserActivityDoc {

    private ObjectId id;
    private UserLike userLike;

}
