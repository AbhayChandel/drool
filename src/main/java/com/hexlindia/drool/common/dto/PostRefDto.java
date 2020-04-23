package com.hexlindia.drool.common.dto;

import com.hexlindia.drool.common.dto.validation.PostRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.PostRefValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
