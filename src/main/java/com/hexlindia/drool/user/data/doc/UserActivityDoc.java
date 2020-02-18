package com.hexlindia.drool.user.data.doc;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
public class UserActivityDoc {

    @Id
    private String id;
    private String userId;
    private UserLike userLike;

}
