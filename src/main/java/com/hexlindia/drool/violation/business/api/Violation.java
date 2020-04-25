package com.hexlindia.drool.violation.business.api;

import com.hexlindia.drool.violation.dto.ViolationReportDto;

import java.util.List;

public interface Violation {

    public List<String> getViolationsTemplate(String postType);

    public boolean save(ViolationReportDto violationReportDto);
}
