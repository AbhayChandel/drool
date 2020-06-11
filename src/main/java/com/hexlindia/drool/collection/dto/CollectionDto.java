package com.hexlindia.drool.collection.dto;

import com.hexlindia.drool.common.data.constant.Visibility;
import com.hexlindia.drool.post.dto.PostDto;
import lombok.Data;

import java.util.Set;

@Data
public class CollectionDto {

    private String id;
    private String name;
    private String about;
    private Visibility visibility;
    private String ownerId;
    private Set<PostDto> posts;

}
