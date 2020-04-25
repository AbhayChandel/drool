package com.hexlindia.drool.violation.data.doc;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.violation.data.VerificationResult;
import com.hexlindia.drool.violation.data.VerificationStatus;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document(collection = "violation_reports")
public class ViolationReportDoc {

    private ObjectId id;
    private List<String> violations;
    private PostRef post;
    private PostRef mainPost;
    private UserRef postOwner;
    private UserRef reportedBy;
    private LocalDateTime dateReported;
    private VerificationStatus status;
    private VerificationResult result;
    private String remarks;
}
