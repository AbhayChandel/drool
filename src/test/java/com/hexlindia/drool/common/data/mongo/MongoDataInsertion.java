package com.hexlindia.drool.common.data.mongo;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MongoDataInsertion {

    public void insertData() {
        insertUserData();
    }

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    PasswordEncoder passwordEncoder;

    public ObjectId insertUserData() {
        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();
        userAccountDocPriyanka.setEmailId("priyanka.singh@gmail.com");
        userAccountDocPriyanka.setPassword(passwordEncoder.encode("priyanka"));
        userAccountDocPriyanka.setActive(true);
        mongoOperations.save(userAccountDocPriyanka);
        ObjectId id = userAccountDocPriyanka.getId();

        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(id);
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Eshika Sharama");
        userProfileDoc.setUsername("EshikaLove");
        mongoOperations.save(userProfileDoc);

        System.out.println("User data inserted successfully");
        return id;
    }
}
