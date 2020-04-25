package com.hexlindia.drool.violation.services.impl.rest;

import com.hexlindia.drool.violation.business.api.Violation;
import com.hexlindia.drool.violation.dto.ViolationReportDto;
import com.hexlindia.drool.violation.services.api.rest.ViolationRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ViolationRestServiceImpl implements ViolationRestService {

    private final Violation violation;

    @Override
    public ResponseEntity<List<String>> getViolationsTemplate(String postType) {
        return ResponseEntity.ok(this.violation.getViolationsTemplate(postType));
    }

    @Override
    public ResponseEntity<Boolean> post(ViolationReportDto violationReportDto) {
        return ResponseEntity.ok(this.violation.save(violationReportDto));
    }
}
