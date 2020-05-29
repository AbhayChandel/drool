package com.hexlindia.drool.post.view;

import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.common.view.UsercardView;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.MappedSuperclass;
import javax.persistence.SqlResultSetMapping;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
@SqlResultSetMapping(name = "PostPageViewMapping",
        classes = {
                @ConstructorResult(
                        targetClass = PostPageView.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "type", type = String.class),
                                @ColumnResult(name = "title", type = String.class),
                                @ColumnResult(name = "date_posted", type = LocalDateTime.class),
                                @ColumnResult(name = "likes", type = Integer.class),
                                @ColumnResult(name = "views", type = Integer.class),
                                @ColumnResult(name = "owner_id", type = Long.class),
                                @ColumnResult(name = "owner_username", type = String.class),
                                @ColumnResult(name = "source_video_id", type = String.class),
                                @ColumnResult(name = "text", type = String.class),
                                @ColumnResult(name = "cover_picture", type = String.class)

                        })
        })
public class PostPageView {

    public PostPageView(Long id, String type, String title, LocalDateTime datePosted, Integer likes, Integer views,
                        Long ownerId, String ownerUsername, String sourceVideoId, String text, String coverPicture) {
        this.id = String.valueOf(id);
        this.type = type;
        this.title = title;
        this.datePosted = String.valueOf(datePosted);
        this.likes = MetaFieldValueFormatter.getCompactFormat(likes);
        this.views = MetaFieldValueFormatter.getCompactFormat(views);
        this.usercardView = new UsercardView(String.valueOf(ownerId), String.valueOf(ownerUsername));
        this.sourceVideoId = sourceVideoId;
        this.text = text;
        this.coverPicture = coverPicture;
    }

    private String id;
    private String type;
    private String title;
    private String datePosted;
    private String likes;
    private String views;
    private UsercardView usercardView;
    private String sourceVideoId;
    private String text;
    private String coverPicture;


}


