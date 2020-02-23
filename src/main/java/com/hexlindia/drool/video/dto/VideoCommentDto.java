package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.video.dto.validation.VideoCommentDeleteValidation;
import com.hexlindia.drool.video.dto.validation.VideoCommentInsertValidation;
import com.hexlindia.drool.video.dto.validation.VideoCommentRefLikeValidation;
import com.hexlindia.drool.video.dto.validation.VideoCommentRefValidation;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class VideoCommentDto {

    @NotEmpty(message = "Comment Id is missing", groups = {VideoCommentDeleteValidation.class, VideoCommentRefValidation.class})
    private String id;

    @NotNull(message = "Post details are missing", groups = {VideoCommentInsertValidation.class, VideoCommentRefValidation.class})
    @Valid
    private PostRefDto postRefDto;

    @NotNull(message = "User details are missing", groups = {VideoCommentInsertValidation.class, VideoCommentRefValidation.class})
    @Valid
    private UserRefDto userRefDto;
    private String datePosted;

    @NotEmpty(message = "Comment is missing", groups = {VideoCommentInsertValidation.class, VideoCommentRefValidation.class})
    private String comment;

    @NotEmpty(message = "Comment likes is missing", groups = {VideoCommentRefLikeValidation.class})
    private String likes;

    public VideoCommentDto(PostRefDto postRefDto, UserRefDto userRefDto, String comment) {
        this.postRefDto = postRefDto;
        this.userRefDto = userRefDto;
        this.comment = comment;
    }

    public VideoCommentDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PostRefDto getPostRefDto() {
        return postRefDto;
    }

    public void setPostRefDto(PostRefDto postRefDto) {
        this.postRefDto = postRefDto;
    }

    public UserRefDto getUserRefDto() {
        return userRefDto;
    }

    public void setUserRefDto(UserRefDto userRefDto) {
        this.userRefDto = userRefDto;
    }

    public String getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
