package com.hexlindia.drool.common.datamigration.test.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ChangeLog
public class UserChangeLog_4 {

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @ChangeSet(order = "006", id = "user", author = "")
    public void insertUsers(MongoTemplate mongoTemplate) {

        UserAccountDoc userAccountDocCommunity = new UserAccountDoc();
        userAccountDocCommunity.setEmailId("community.drool@gmail.com");
        userAccountDocCommunity.setPassword(passwordEncoder.encode("community"));
        userAccountDocCommunity.setUsername("Community");
        userAccountDocCommunity.setActive(true);
        mongoTemplate.save(userAccountDocCommunity);

        UserProfileDoc userProfileDocCommunity = new UserProfileDoc();
        userProfileDocCommunity.setId(userAccountDocCommunity.getId());
        userProfileDocCommunity.setCity("Cloud");
        userProfileDocCommunity.setGender("N");
        userProfileDocCommunity.setMobile("");
        userProfileDocCommunity.setName("Drool Community");
        userProfileDocCommunity.setUsername("Community");
        mongoTemplate.save(userProfileDocCommunity);


        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();
        userAccountDocPriyanka.setEmailId("priyanka.singh@gmail.com");
        userAccountDocPriyanka.setPassword(passwordEncoder.encode("priyanka"));
        userAccountDocPriyanka.setUsername("PriyankaLove");
        userAccountDocPriyanka.setActive(true);
        mongoTemplate.save(userAccountDocPriyanka);

        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(userAccountDocPriyanka.getId());
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Priyanka Singh");
        userProfileDoc.setUsername("PriyankaLove");
        mongoTemplate.save(userProfileDoc);

        UserAccountDoc userAccountDocSonam = new UserAccountDoc();
        userAccountDocSonam.setEmailId("sonam.kapoor@gmail.com");
        userAccountDocSonam.setPassword(passwordEncoder.encode("sonam"));
        userAccountDocSonam.setUsername("Sonamstyle");
        userAccountDocSonam.setActive(true);
        mongoTemplate.save(userAccountDocSonam);

        UserProfileDoc userProfileDocSonam = new UserProfileDoc();
        userProfileDocSonam.setId(userAccountDocSonam.getId());
        userProfileDocSonam.setCity("Indore");
        userProfileDocSonam.setGender("F");
        userProfileDocSonam.setMobile("9876543340");
        userProfileDocSonam.setName("Sonam Kapoor");
        userProfileDocSonam.setUsername("Sonamstyle");
        mongoTemplate.save(userProfileDocSonam);
    }
}
