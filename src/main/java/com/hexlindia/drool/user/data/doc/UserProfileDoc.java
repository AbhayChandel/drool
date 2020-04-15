package com.hexlindia.drool.user.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user_profiles")
public class UserProfileDoc {

    private ObjectId id;

    private String username;
    private String name;

    private String mobile;
    private String city;
    private String gender;
}
