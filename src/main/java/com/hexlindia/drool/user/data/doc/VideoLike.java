package com.hexlindia.drool.user.data.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
@AllArgsConstructor
public class VideoLike {

    private ObjectId videoId;
    private String title;
}
