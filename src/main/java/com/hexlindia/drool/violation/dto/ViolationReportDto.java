package com.hexlindia.drool.violation.dto;

import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.violation.data.VerificationResult;
import com.hexlindia.drool.violation.data.VerificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ViolationReportDto {
    private String id;
    private List<String> violations;
    private PostRefDto post;
    private PostRefDto mainPost;
    private UserRefDto postOwner;
    private UserRefDto reportedBy;
    private LocalDateTime dateReported;
    private VerificationStatus status;
    private VerificationResult result;
    private String remarks;
}
