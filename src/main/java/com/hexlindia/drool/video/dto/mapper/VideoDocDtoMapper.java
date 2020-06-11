package com.hexlindia.drool.video.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.StringToObjectIdMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.product.dto.mapper.ProductRefMapper;
import com.hexlindia.drool.video.data.doc.VideoDoc;
import com.hexlindia.drool.video.dto.VideoDtoMOngo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {ProductRefMapper.class, UserRefMapper.class, VideoCommentMapper.class, ObjectIdMapper.class})
public abstract class VideoDocDtoMapper {

    @Mapping(target = "productRefList", source = "productRefDtoList")
    @Mapping(target = "userRef", source = "userRefDto")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = StringToObjectIdMapping.class)
    @Mapping(source = "id", target = "id", qualifiedBy = StringToObjectIdMapping.class)
    public abstract VideoDoc toDoc(VideoDtoMOngo videoDtoMOngo);

    @Mapping(target = "productRefDtoList", source = "productRefList")
    @Mapping(target = "userRefDto", source = "userRef")
    @Mapping(target = "videoCommentDtoList", source = "commentList")
    @Mapping(source = "reviewId", target = "reviewId", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(source = "id", target = "id", qualifiedBy = ObjectIdToStringMapping.class)
    public abstract VideoDtoMOngo toDto(VideoDoc videoDoc);

    @AfterMapping
    protected void thisIsCalledAfterMappingIsDone(VideoDoc videoDoc, @MappingTarget VideoDtoMOngo videoDtoMOngo) {
        videoDtoMOngo.setDatePosted(MetaFieldValueFormatter.getDateInDayMonCommaYear(videoDoc.getDatePosted()));
        videoDtoMOngo.setLikes(MetaFieldValueFormatter.getCompactFormat(videoDoc.getLikes()));
        videoDtoMOngo.setViews(MetaFieldValueFormatter.getCompactFormat(videoDoc.getViews()));
    }
}
