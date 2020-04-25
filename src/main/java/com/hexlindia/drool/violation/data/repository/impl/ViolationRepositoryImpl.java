package com.hexlindia.drool.violation.data.repository.impl;

import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.data.doc.ViolationTemplateDoc;
import com.hexlindia.drool.violation.data.repository.api.ViolationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ViolationRepositoryImpl implements ViolationRepository {

    private final MongoOperations mongoOperations;

    @Override
    public List<String> getViolationsTemplate(String postType) {
        ViolationTemplateDoc violationTemplateDoc = mongoOperations.findOne(Query.query(new Criteria("postType").is(postType)), ViolationTemplateDoc.class);
        if (violationTemplateDoc != null) {
            return violationTemplateDoc.getViolations();
        }
        return null;
    }

    @Override
    public ViolationReportDoc save(ViolationReportDoc violationReport) {
        return mongoOperations.save(violationReport);
    }
}
