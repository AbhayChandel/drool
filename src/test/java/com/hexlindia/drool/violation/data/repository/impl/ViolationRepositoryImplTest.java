package com.hexlindia.drool.violation.data.repository.impl;

import com.hexlindia.drool.common.data.constant.PostMedium;
import com.hexlindia.drool.common.data.constant.PostType;
import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.violation.data.VerificationStatus;
import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.data.doc.ViolationTemplateDoc;
import com.hexlindia.drool.violation.data.repository.api.ViolationRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ViolationRepositoryImplTest {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    ViolationRepository violationRepository;

    @BeforeEach
    void setUp() {
        insertViolationTemplates();
    }

    private void insertViolationTemplates() {
        ViolationTemplateDoc violationTemplateDoc = new ViolationTemplateDoc();
        violationTemplateDoc.setPostType("reply");
        violationTemplateDoc.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Off-topic", "Advertisement for a product"));
        mongoOperations.save(violationTemplateDoc);
    }

    @Test
    void getViolations() {
        List<String> violations = violationRepository.getViolationsTemplate("reply");
        assertEquals(6, violations.size());
    }

    @Test
    void save() {
        ViolationReportDoc violationReportDoc = new ViolationReportDoc();
        violationReportDoc.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        ObjectId postId = ObjectId.get();
        violationReportDoc.setPost(new PostRef(postId, "This is an ordinary comment", PostType.comment, PostMedium.text, LocalDateTime.now()));
        ObjectId postOwnerId = ObjectId.get();
        violationReportDoc.setPostOwner(new UserRef(postOwnerId, "priyanka"));
        ObjectId reportingUserId = ObjectId.get();
        violationReportDoc.setReportedBy(new UserRef(reportingUserId, "sonam99"));
        violationReportDoc.setDateReported(LocalDateTime.now());
        violationReportDoc.setStatus(VerificationStatus.pending);
        violationReportDoc = violationRepository.save(violationReportDoc);
        assertNotNull(violationReportDoc.getId());
    }
}