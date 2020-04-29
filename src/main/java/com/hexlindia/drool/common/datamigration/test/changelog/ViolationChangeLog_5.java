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
        violationTemplateDocReply.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Not a discussion topic", "Advertisement for a product"));
        mongoTemplate.save(violationTemplateDocReply);

        ViolationTemplateDoc violationTemplateDocTopic = new ViolationTemplateDoc();
        violationTemplateDocTopic.setPostType("topic");
        violationTemplateDocTopic.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Off-topic", "Advertisement for a product"));
        mongoTemplate.save(violationTemplateDocTopic);
    }
}
