package com.hexlindia.drool.common.dto.mapper;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdMapper {

    @StringToObjectIdMapping
    public static ObjectId stringToObjectId(String id) {
        return new ObjectId(id);
    }

    @ObjectIdToStringMapping
    public static String ObjectIdToString(ObjectId id) {
        return id.toHexString();
    }


}
