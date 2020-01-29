package com.hexlindia.drool.video.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hexlindia.drool.common.view.UserProfileCardView;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;

@MappedSuperclass
@SqlResultSetMapping(name = "videoCardView",
        classes = {
                @ConstructorResult(
                        targetClass = VideoCardView.class,
                        columns = {
                                @ColumnResult(name = "videoId", type = Long.class),
                                @ColumnResult(name = "postType", type = String.class),
                                @ColumnResult(name = "title", type = String.class),
                                @ColumnResult(name = "userId", type = Long.class),
                                @ColumnResult(name = "sourceVideoId", type = String.class),
                                @ColumnResult(name = "datePosted", type = String.class),
                                @ColumnResult(name = "views", type = Integer.class),
                                @ColumnResult(name = "likes", type = Integer.class),
                                @ColumnResult(name = "description", type = String.class),
                                @ColumnResult(name = "commentCount", type = Integer.class),
                                @ColumnResult(name = "username", type = String.class)

                        })
        })
public class VideoCardView {

    @JsonProperty("videoDetails")
    private VideoView videoView;

    @JsonProperty("userCard")
    private UserProfileCardView userProfileCardView;

    public VideoCardView(Long videoId, String postType, String title, Long userId, String sourceVideoId, String datePosted, int views, int likes, String description, Integer commentCount, String username) {
        videoView = new VideoView(Long.toString(videoId), postType, title, Long.toString(userId), sourceVideoId, datePosted, Integer.toString(views), Integer.toString(likes), description, Integer.toString(commentCount));
        userProfileCardView = new UserProfileCardView(userId, username);

    }

    public VideoCardView() {
    }

    public VideoView getVideoView() {
        return videoView;
    }

    public UserProfileCardView getUserProfileCardView() {
        return userProfileCardView;
    }
}
