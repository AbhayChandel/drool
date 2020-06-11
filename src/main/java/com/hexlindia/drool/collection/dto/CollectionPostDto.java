package com.hexlindia.drool.collection.dto;

import com.hexlindia.drool.collection.services.validation.CollectionPostValidation;
import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.common.data.constant.Visibility;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CollectionPostDto {
    private String collectionId;
    private String name;
    private String about;
    private Visibility visibility;

    private String ownerId;

    @NotEmpty(message = "Post Id is empty", groups = {CollectionPostValidation.class})
    private String postId;

    @NotNull(message = "Post type is empty", groups = {CollectionPostValidation.class})
    private PostType2 postType;
}
