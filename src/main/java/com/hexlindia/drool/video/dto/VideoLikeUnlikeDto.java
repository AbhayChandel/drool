package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.validation.PostRefValidation;
import com.hexlindia.drool.video.dto.validation.VideoDecrementLikesValidation;
import com.hexlindia.drool.video.dto.validation.VideoIncrementLikesValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VideoLikeUnlikeDto {

    @NotEmpty(message = "User Id is missing", groups = {VideoIncrementLikesValidation.class, VideoDecrementLikesValidation.class})
    private String userId;

    @NotEmpty(message = "Video Id is missing", groups = {VideoIncrementLikesValidation.class, VideoDecrementLikesValidation.class})
    private String videoId;

    @NotEmpty(message = "Video title is missing", groups = {VideoIncrementLikesValidation.class})
    private String videoTitle;

    @NotNull(message = "Post type is missing", groups = {PostRefValidation.class})
    private PostType postType;

    @NotNull(message = "Post medium is missing", groups = {PostRefValidation.class})
    private PostMedium postMedium;
}
