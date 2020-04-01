package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.product.dto.mapper.ProductRefMapper;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductRefMapper.class, UserRefMapper.class, VideoCommentMapper.class, ObjectIdMapper.class})
public abstract class VideoDocDtoMapper {

    @Mapping(target = "productRefList", source = "productRefDtoList")
    @Mapping(target = "userRef", source = "userRefDto")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = StringToObjectIdMapping.class)
    public abstract VideoDoc
    toDoc(VideoDto videoDto);

    @Mapping(target = "productRefDtoList", source = "productRefList")
    @Mapping(target = "userRefDto", source = "userRef")
    @Mapping(target = "videoCommentDtoList", source = "commentList")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = ObjectIdToStringMapping.class)
    public abstract VideoDto toDto(VideoDoc videoDoc);

    @AfterMapping
    protected void thisIsCalledAfterMappingIsDone(VideoDoc videoDoc, @MappingTarget VideoDto videoDto) {
        videoDto.setDatePosted(MetaFieldValueFormatter.getDateInDayMonCommaYear(videoDoc.getDatePosted()));
        videoDto.setLikes(MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes()));
        videoDto.setViews(MetaFieldValueFormatter.getCompactFormat(videoDoc.getViews()));
    }
}
