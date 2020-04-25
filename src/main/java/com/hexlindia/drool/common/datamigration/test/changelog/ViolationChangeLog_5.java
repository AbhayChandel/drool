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
        ViolationTemplateDoc violationTemplateDoc = new ViolationTemplateDoc();
        violationTemplateDoc.setPostType("reply");
        violationTemplateDoc.setViolations(Arrays.asList("Use of bad language", "Disrespectful to a person/community/race/religion", "Sexual/Vulgar", "Content piracy", "Off-topic", "Advertisement for a product"));
        mongoTemplate.save(violationTemplateDoc);
    }
}
