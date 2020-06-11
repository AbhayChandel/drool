package com.hexlindia.drool.video.dto;

import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.product.dto.ProductRefDto;
import com.hexlindia.drool.video.dto.validation.VideoInsertValidation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class VideoDtoMOngo {

    private String id;

    @NotNull(message = "Video type is missing", groups = {VideoInsertValidation.class})
    private PostType type;

    private String reviewId;

    private boolean active;

    @NotEmpty(message = "Video title is missing", groups = {VideoInsertValidation.class})
    private String title;
    private String description;

    @NotEmpty(message = "Video Source ID is missing", groups = {VideoInsertValidation.class})
    private String sourceId;
    private String datePosted;
    private String likes;
    private String views;

    @NotEmpty(message = "Product(s) are not tagged for the video", groups = {VideoInsertValidation.class})
    private List<ProductRefDto> productRefDtoList;

    @NotNull(message = "User info is missing", groups = {VideoInsertValidation.class})
    private UserRefDto userRefDto;
    private List<VideoCommentDto> videoCommentDtoList;
    private int totalComments;

    public VideoDtoMOngo(PostType type, String title, String description, String sourceId, List<ProductRefDto> productRefDtoList, UserRefDto userRefDto) {
        this.type = type;
        this.title = title;
        this.description = description;
        this.sourceId = sourceId;
        this.productRefDtoList = productRefDtoList;
        this.userRefDto = userRefDto;
    }
}
