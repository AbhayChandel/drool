package com.hexlindia.drool.violation.data.doc;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "violation_templates")
public class ViolationTemplateDoc {

    String postType;
    List<String> violations;
}
