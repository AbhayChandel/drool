package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.violation.data.doc.ViolationTemplateDoc;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

@ChangeLog
public class ViolationChangeLog_5 {

    @ChangeSet(order = "007", id = "violationTemplate", author = "")
    public void insertViolationTemplate(MongoTemplate mongoTemplate) {
        ViolationTemplateDoc violationTemplateDocReply = new ViolationTemplateDoc();
        violationTemplateDocReply.setPostType("reply");
        violationTemplateDocReply.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Not a discussion topic", "Advertising/Selling product(s)"));
        mongoTemplate.save(violationTemplateDocReply);

        ViolationTemplateDoc violationTemplateDocTopic = new ViolationTemplateDoc();
        violationTemplateDocTopic.setPostType("topic");
        violationTemplateDocTopic.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Off-topic", "Advertising/Selling product(s)"));
        mongoTemplate.save(violationTemplateDocTopic);

        ViolationTemplateDoc violationTemplateDocReview = new ViolationTemplateDoc();
        violationTemplateDocReview.setPostType("review");
        violationTemplateDocReview.setViolations(Arrays.asList("Is not a review", "Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Content do not match title/description/tags", "Advertising/Selling product(s)"));
        mongoTemplate.save(violationTemplateDocReview);

        ViolationTemplateDoc violationTemplateDocGuide = new ViolationTemplateDoc();
        violationTemplateDocGuide.setPostType("guide");
        violationTemplateDocGuide.setViolations(Arrays.asList("Is not a review", "Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Content do not match title/description/tags", "Advertising/Selling product(s)"));
        mongoTemplate.save(violationTemplateDocGuide);

        ViolationTemplateDoc violationTemplateDocComment = new ViolationTemplateDoc();
        violationTemplateDocComment.setPostType("comment");
        violationTemplateDocComment.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Off-topic", "Advertising/Selling product(s)"));
        mongoTemplate.save(violationTemplateDocComment);
    }
}
