package com.hexlindia.drool.video.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.common.view.UserProfileCardView;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;

@MappedSuperclass
@SqlResultSetMapping(name = "videoCommentCardView",
        classes = {
                @ConstructorResult(
                        targetClass = VideoCommentCardView.class,
                        columns = {
                                @ColumnResult(name = "commentId", type = Long.class),
                                @ColumnResult(name = "videoId", type = Long.class),
                                @ColumnResult(name = "comment", type = String.class),
                                @ColumnResult(name = "userId", type = Long.class),
                                @ColumnResult(name = "datePosted", type = LocalDateTime.class),
                                @ColumnResult(name = "likes", type = Integer.class),
                                @ColumnResult(name = "username", type = String.class)
                        })
        })
public class VideoCommentCardView {

    @JsonProperty("videoCommentView")
    private VideoCommentView videoCommentView;

    @JsonProperty("userCard")
    private UserProfileCardView userProfileCardView;

    public VideoCommentCardView(Long commentId, Long videoId, String comment, Long userId, LocalDateTime datePosted, Integer likes, String username) {
        videoCommentView = new VideoCommentView(Long.toString(commentId), Long.toString(videoId), comment, Long.toString(userId), MetaFieldValueFormatter.getDateTimeInDayMonCommaYear(datePosted), MetaFieldValueFormatter.getCompactFormat(likes));
        userProfileCardView = new UserProfileCardView(Long.toString(userId), username);
    }

    public VideoCommentCardView() {
    }

    public VideoCommentView getVideoCommentView() {
        return videoCommentView;
    }

    public UserProfileCardView getUserProfileCardView() {
        return userProfileCardView;
    }
}
