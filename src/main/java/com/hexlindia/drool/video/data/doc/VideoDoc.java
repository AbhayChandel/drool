package com.hexlindia.drool.video.data.doc;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.product.data.doc.ProductRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Document(collection = "videos")
public class VideoDoc {

    @Id
    private ObjectId id;
    private PostType type;
    private ObjectId reviewId;
    private boolean active;
    private String title;
    private String description;
    private String sourceId;
    private LocalDateTime datePosted;
    private int likes;
    private int views;
    private List<ProductRef> productRefList;
    private UserRef userRef;
    private List<VideoComment> commentList;
    private int totalComments;

    public VideoDoc(PostType type, String title, String description, String sourceId, List<ProductRef> productRefList, UserRef userRef) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sourceId = sourceId;
        this.productRefList = productRefList;
        this.userRef = userRef;
    }
}
