package com.hexlindia.drool.post.dto;

import com.hexlindia.drool.common.data.constant.PostFormat;
import com.hexlindia.drool.common.data.constant.PostType;
import lombok.Data;

@Data
public class PostDto {
    private String id;
    private String title;
    private String datePosted;
    private String likes;
    private String views;
    private PostFormat postFormat;
    private PostType postType;
    private String owner;
    private String sourceVideoId;
    private String text;
    private String coverPicture;


}
