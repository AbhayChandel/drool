package com.hexlindia.drool.post.dto;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.services.validation.PostValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDto {
    private String id;

    @NotEmpty(message = "Title is missing", groups = {PostValidation.class})
    private String title;
    private String datePosted;
    private String likes;
    private String views;

    @NotNull(message = "Post type is missing", groups = {PostValidation.class})
    private PostType2 type;

    @NotEmpty(message = "User Id is missing", groups = {PostValidation.class})
    private String ownerId;
    private String sourceVideoId;
    private String text;
    private String coverPicture;
}
