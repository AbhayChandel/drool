package com.hexlindia.drool.activity.data.doc;

import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "activity_feed")
public class FeedDoc {

    private ObjectId postId;
    private String postType;
    private String postMedium;
    private String title;
    private String sourceId;
    private LocalDateTime datePosted;
    private int likes;
    private int views;
    private int comments;
    private List<ProductRef> productRefList;
    private UserRef userRef;

}
