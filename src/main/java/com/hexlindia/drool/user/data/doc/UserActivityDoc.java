package com.hexlindia.drool.user.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document
@Getter
@Setter
public class UserActivityDoc {

    @Id
    private String id;
    private String userId;
    private UserLike userLike;

}
