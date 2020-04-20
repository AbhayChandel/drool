package com.hexlindia.drool.activity.dto.mapper;

import com.hexlindia.drool.activity.data.doc.FeedDoc;
import com.hexlindia.drool.activity.dto.FeedDto;
import com.hexlindia.drool.common.dto.mapper.ObjectIdMapper;
import com.hexlindia.drool.common.dto.mapper.ObjectIdToStringMapping;
import com.hexlindia.drool.common.dto.mapper.UserRefMapper;
import com.hexlindia.drool.common.util.MetaFieldValueFormatter;
import com.hexlindia.drool.product.dto.mapper.ProductRefMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductRefMapper.class, UserRefMapper.class, ObjectIdMapper.class})
public abstract class FeedDocDtoMapper {

    @Mapping(source = "postId", target = "postId", qualifiedBy = ObjectIdToStringMapping.class)
    @Mapping(target = "productRefDtoList", source = "productRefList")
    @Mapping(target = "userRefDto", source = "userRef")
    public abstract FeedDto toDto(FeedDoc feedDoc);

    public abstract List<FeedDto> toDtoList(List<FeedDoc> feedDocList);

    @AfterMapping
    protected void afterMappingToDto(FeedDoc feedDoc, @MappingTarget FeedDto feedDto) {
        feedDto.setDatePosted(MetaFieldValueFormatter.getDateInDayMonCommaYear(feedDoc.getDatePosted()));
        feedDto.setComments(MetaFieldValueFormatter.getCompactFormat(feedDoc.getComments()));
        feedDto.setLikes(MetaFieldValueFormatter.getCompactFormat(feedDoc.getLikes()));
        feedDto.setViews(MetaFieldValueFormatter.getCompactFormat(feedDoc.getViews()));
    }
}
