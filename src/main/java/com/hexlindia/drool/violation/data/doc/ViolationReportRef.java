package com.hexlindia.drool.violation.data.doc;

import com.hexlindia.drool.common.data.doc.PostRef;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ViolationReportRef {

    ObjectId id;
    private List<String> violations;
    private PostRef post;
    private PostRef mainPost;
    private LocalDateTime dateReported;

}
