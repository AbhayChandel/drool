package com.hexlindia.drool.discussion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyPostValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionReplyUpdateValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DiscussionReplyDto {

    @NotNull(message = "Reply Id cannot be null", groups = {DiscussionReplyUpdateValidation.class})
    private String id;

    @NotNull(message = "Discussion Id cannot be null", groups = {DiscussionReplyPostValidation.class})
    private String discussionId;

    @NotEmpty(message = "Reply cannot be empty", groups = {DiscussionReplyPostValidation.class, DiscussionReplyUpdateValidation.class})
    private String reply;

    @NotNull(message = "User Id cannot be null", groups = {DiscussionReplyPostValidation.class})
    @JsonProperty("user")
    private UserRefDto userRefDto;

    private String datePosted;
    private String likes;
}
