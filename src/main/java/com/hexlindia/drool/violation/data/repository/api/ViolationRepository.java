package com.hexlindia.drool.violation.data.repository.api;

import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;

import java.util.List;

public interface ViolationRepository {

    List<String> getViolationsTemplate(String postType);

    ViolationReportDoc save(ViolationReportDoc violationReport);
}
