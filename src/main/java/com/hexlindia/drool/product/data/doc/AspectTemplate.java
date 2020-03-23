package com.hexlindia.drool.product.data.doc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "aspect_templates")
@NoArgsConstructor
@Getter
@Setter
public class AspectTemplate {

    private ObjectId id;
    private String title;
    private List<String> options;

}
