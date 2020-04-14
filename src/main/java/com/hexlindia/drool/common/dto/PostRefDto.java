package com.hexlindia.drool.common.dto;

import com.hexlindia.drool.common.dto.validation.PostRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.PostRefValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class PostRefDto {

    @NotEmpty(message = "Post Id is missing", groups = {PostRefValidation.class, PostRefDeleteValidation.class})
    private String id;

    @NotEmpty(message = "Post title is missing", groups = {PostRefValidation.class})
    private String title;

    @NotEmpty(message = "Post type is missing", groups = {PostRefValidation.class})
    private String type;

    @NotEmpty(message = "Post medium is missing", groups = {PostRefValidation.class})
    private String medium;
    private String datePosted;

    public PostRefDto(String id, String title, String type, String medium, String datePosted) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.medium = medium;
        this.datePosted = datePosted;
    }
}
