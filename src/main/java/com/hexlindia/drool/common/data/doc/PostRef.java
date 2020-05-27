package com.hexlindia.drool.common.data.doc;

import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.common.data.constant.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostRef {

    private ObjectId id;
    private String title;
    private PostType type;
    private PostFormat medium;
    private LocalDateTime dateTime;
    private PostRef parentPost;

    public PostRef(ObjectId id, String title, PostType type, PostFormat medium, LocalDateTime dateTime) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.medium = medium;
        this.dateTime = dateTime;
    }
}
