package com.hexlindia.drool.common.dto;

import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.validation.PostRefDeleteValidation;
import com.hexlindia.drool.common.dto.validation.PostRefValidation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRefDto {

    @NotEmpty(message = "Post Id is missing", groups = {PostRefValidation.class, PostRefDeleteValidation.class})
    private String id;

    @NotEmpty(message = "Post title is missing", groups = {PostRefValidation.class})
    private String title;

    @NotNull(message = "Post type is missing", groups = {PostRefValidation.class})
    private PostType type;

    @NotNull(message = "Post medium is missing", groups = {PostRefValidation.class})
    private PostMedium medium;
    private String dateTime;

}
