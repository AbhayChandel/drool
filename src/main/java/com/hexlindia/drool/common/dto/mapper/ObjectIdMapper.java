package com.hexlindia.drool.common.dto.mapper;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ObjectIdMapper {

    @StringToObjectIdMapping
    public ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @ObjectIdToStringMapping
    public String ObjectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }


}
