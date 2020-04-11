package com.hexlindia.drool.discussion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicCreateValidation;
import com.hexlindia.drool.discussion.to.validation.DiscussionTopicUpdateValidation;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class DiscussionTopicDto {

    @NotNull(message = "Discussion Id cannot be null", groups = {DiscussionTopicUpdateValidation.class})
    private String id;

    @NotEmpty(message = "Topic title cannot be empty", groups = {DiscussionTopicCreateValidation.class, DiscussionTopicUpdateValidation.class})
    private String title;

    @NotNull(message = "User Id cannot be null", groups = {DiscussionTopicCreateValidation.class})
    @JsonProperty("user")
    private UserRefDto userRefDto;

    private String datePosted;
    private String dateLastActive;
    private String views;
    private String likes;
    private String replies;
    private boolean active;

    @JsonProperty("replyList")
    private List<DiscussionReplyDto> discussionReplyDtoList;

}
