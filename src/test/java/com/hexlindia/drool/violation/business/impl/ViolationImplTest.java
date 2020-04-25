package com.hexlindia.drool.violation.business.impl;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.violation.business.api.Violation;
import com.hexlindia.drool.violation.data.VerificationStatus;
import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.data.repository.api.ViolationRepository;
import com.hexlindia.drool.violation.dto.mapper.ViolationReportMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ViolationImplTest {

    @Autowired
    Violation violation;

    @MockBean
    ViolationRepository violationRepository;

    @MockBean
    ViolationReportMapper violationReportMapper;

    @Test
    void save() {
        ViolationReportDoc violationReportDoc = new ViolationReportDoc();
        violationReportDoc.setId(ObjectId.get());
        violationReportDoc.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        ObjectId postId = ObjectId.get();
        violationReportDoc.setPost(new PostRef(postId, "This is an ordinary comment", "comment", "text", LocalDateTime.now()));
        ObjectId postOwnerId = ObjectId.get();
        violationReportDoc.setPostOwner(new UserRef(postOwnerId, "priyanka"));
        ObjectId reportingUserId = ObjectId.get();
        violationReportDoc.setReportedBy(new UserRef(reportingUserId, "sonam99"));

        when(this.violationReportMapper.toDoc(any())).thenReturn(violationReportDoc);
        when(this.violationRepository.save(any())).thenReturn(violationReportDoc);

        this.violation.save(null);

        ArgumentCaptor<ViolationReportDoc> violationReportDocArgumentCaptor = ArgumentCaptor.forClass(ViolationReportDoc.class);
        verify(this.violationRepository, times(1)).save(violationReportDocArgumentCaptor.capture());
        assertNotNull(violationReportDocArgumentCaptor.getValue().getDateReported());
        assertEquals(VerificationStatus.pending, violationReportDocArgumentCaptor.getValue().getStatus());

    }
}