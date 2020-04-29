package com.hexlindia.drool.violation.business.impl;

import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.violation.business.api.Violation;
import com.hexlindia.drool.violation.data.VerificationStatus;
import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.data.doc.ViolationReportRefMapper;
import com.hexlindia.drool.violation.data.repository.api.ViolationRepository;
import com.hexlindia.drool.violation.dto.ViolationReportDto;
import com.hexlindia.drool.violation.dto.mapper.ViolationReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ViolationImpl implements Violation {

    private final ViolationRepository violationRepository;
    private final ViolationReportMapper violationReportMapper;
    private final UserActivity userActivity;
    private final ViolationReportRefMapper violationReportRefMapper;

    @Override
    public List<String> getViolationsTemplate(String postType) {
        return violationRepository.getViolationsTemplate(postType);
    }

    @Override
    public boolean save(ViolationReportDto violationReportDto) {
        ViolationReportDoc violationReportDoc = violationReportMapper.toDoc(violationReportDto);
        violationReportDoc.setDateReported(LocalDateTime.now());
        violationReportDoc.setStatus(VerificationStatus.pending);
        violationReportDoc = violationRepository.save(violationReportDoc);
        if (violationReportDoc.getId() != null) {
            return userActivity.addViolation(violationReportDoc.getPostOwner().getId(), violationReportDoc.getReportedBy().getId(), violationReportRefMapper.toRef(violationReportDoc));
        }
        return false;
    }
}
