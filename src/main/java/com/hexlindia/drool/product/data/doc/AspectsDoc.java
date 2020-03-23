package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Getter
@Setter
public class AspectsDoc {

    @Field(value = "aspect_results")
    private List<AspectResultDoc> aspectResultDocList;

    @Field(value = "external_aspects")
    private List<ObjectId> externalAspectIds;

    @Field(value = "internal_aspects")
    private List<AspectTemplate> internalAspects;
}
