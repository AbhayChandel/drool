package com.hexlindia.drool.violation.dto.mapper;

import com.hexlindia.drool.common.data.doc.PostRef;
import com.hexlindia.drool.common.dto.PostRefDto;
import com.hexlindia.drool.common.dto.UserRefDto;
import com.hexlindia.drool.user.data.doc.UserRef;
import com.hexlindia.drool.violation.data.VerificationResult;
import com.hexlindia.drool.violation.data.VerificationStatus;
import com.hexlindia.drool.violation.data.doc.ViolationReportDoc;
import com.hexlindia.drool.violation.dto.ViolationReportDto;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ViolationReportMapperTest {

    @Autowired
    ViolationReportMapper violationReportMapper;

    @Test
    void toDoc() {
        ViolationReportDto violationReportDto = new ViolationReportDto();
        violationReportDto.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        ObjectId postId = ObjectId.get();
        violationReportDto.setPost(new PostRefDto(postId.toHexString(), "This is an ordinary comment", "comment", "text", null));
        ObjectId postOwnerId = ObjectId.get();
        violationReportDto.setPostOwner(new UserRefDto(postOwnerId.toHexString(), "priyanka"));
        ObjectId reportingUserId = ObjectId.get();
        violationReportDto.setReportedBy(new UserRefDto(reportingUserId.toHexString(), "sonam99"));
        violationReportDto.setDateReported(LocalDateTime.now());
        violationReportDto.setStatus(VerificationStatus.completed);
        violationReportDto.setResult(VerificationResult.VIOLATION);
        violationReportDto.setRemarks("Tech team has been asked to remove the post");

        ViolationReportDoc violationReportDoc = violationReportMapper.toDoc(violationReportDto);

        assertEquals(2, violationReportDoc.getViolations().size());
        assertEquals("Bad Language", violationReportDoc.getViolations().get(0));
        assertEquals("Sexual Content", violationReportDoc.getViolations().get(1));
        assertEquals(postId, violationReportDoc.getPost().getId());
        assertEquals("This is an ordinary comment", violationReportDoc.getPost().getTitle());
        assertEquals("comment", violationReportDoc.getPost().getType());
        assertEquals("text", violationReportDoc.getPost().getMedium());
        assertEquals(postOwnerId, violationReportDoc.getPostOwner().getId());
        assertEquals("priyanka", violationReportDoc.getPostOwner().getUsername());
        assertEquals(reportingUserId, violationReportDoc.getReportedBy().getId());
        assertEquals("sonam99", violationReportDoc.getReportedBy().getUsername());
        assertEquals(VerificationStatus.completed, violationReportDoc.getStatus());
        assertEquals(VerificationResult.VIOLATION, violationReportDoc.getResult());
        assertEquals("Tech team has been asked to remove the post", violationReportDoc.getRemarks());
    }

    @Test
    void toDto() {
        ViolationReportDoc violationReportDoc = new ViolationReportDoc();
        ObjectId violationReportId = ObjectId.get();
        violationReportDoc.setId(violationReportId);
        violationReportDoc.setViolations(Arrays.asList("Bad Language", "Sexual Content"));
        ObjectId postId = ObjectId.get();
        violationReportDoc.setPost(new PostRef(postId, "This is an ordinary comment", "comment", "text", LocalDateTime.now()));
        ObjectId postOwnerId = ObjectId.get();
        violationReportDoc.setPostOwner(new UserRef(postOwnerId, "priyanka"));
        ObjectId reportingUserId = ObjectId.get();
        violationReportDoc.setReportedBy(new UserRef(reportingUserId, "sonam99"));
        violationReportDoc.setDateReported(LocalDateTime.now());
        violationReportDoc.setStatus(VerificationStatus.completed);
        violationReportDoc.setResult(VerificationResult.VIOLATION);
        violationReportDoc.setRemarks("Tech team has been asked to remove the post");

        ViolationReportDto violationReportDto = violationReportMapper.toDto(violationReportDoc);

        assertEquals(violationReportId.toHexString(), violationReportDto.getId());
        assertEquals(2, violationReportDto.getViolations().size());
        assertEquals("Bad Language", violationReportDto.getViolations().get(0));
        assertEquals("Sexual Content", violationReportDto.getViolations().get(1));
        assertEquals(postId.toHexString(), violationReportDto.getPost().getId());
        assertEquals("This is an ordinary comment", violationReportDto.getPost().getTitle());
        assertEquals("comment", violationReportDto.getPost().getType());
        assertEquals("text", violationReportDto.getPost().getMedium());
        assertEquals(postOwnerId.toHexString(), violationReportDto.getPostOwner().getId());
        assertEquals("priyanka", violationReportDto.getPostOwner().getUsername());
        assertEquals(reportingUserId.toHexString(), violationReportDto.getReportedBy().getId());
        assertEquals("sonam99", violationReportDto.getReportedBy().getUsername());
        assertEquals(VerificationStatus.completed, violationReportDto.getStatus());
        assertEquals(VerificationResult.VIOLATION, violationReportDto.getResult());
        assertEquals("Tech team has been asked to remove the post", violationReportDto.getRemarks());
    }
}