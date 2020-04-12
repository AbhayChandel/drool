package com.hexlindia.drool.user.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "accounts")
public class UserAccountDoc {

    private ObjectId id;

    private String emailId;
    private String password;
    private boolean active;

}
