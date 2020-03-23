package com.hexlindia.drool.common.dto.mapper;

import org.bson.types.ObjectId;

public class ObjectIdToStringMapper {

    @ObjectIdToStringMappingAnnotation
    public static String ObjectIdToString(ObjectId id) {
        return id.toHexString();
    }
}
