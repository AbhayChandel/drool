package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.data.repository.api.UserProfileRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(MongoDBConfig.class)
class UserProfileRepositoryImplTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    MongoOperations mongoOperations;

    private static ObjectId insertedProfileAccountId = new ObjectId();
    private UserProfileDoc userProfileDoc = null;

    @BeforeEach
    void setUp() {
        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(insertedProfileAccountId);
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Eshika Sharama");
        userProfileDoc.setUsername("EshikaLove");
        mongoOperations.save(userProfileDoc);
        this.userProfileDoc = userProfileDoc;
    }

    @Test
    void save() {
        UserProfileDoc userProfileDoc = new UserProfileDoc();
        userProfileDoc.setId(ObjectId.get());
        userProfileDoc.setCity("Bilaspur");
        userProfileDoc.setGender("F");
        userProfileDoc.setMobile("9876543210");
        userProfileDoc.setName("Eshika Sharama");
        userProfileDoc.setUsername("EshikaPop");

        assertNotNull(userProfileRepository.save(userProfileDoc));
    }

    @Test
    void findById() {
        assertTrue(userProfileRepository.findById(insertedProfileAccountId).isPresent());
    }

    @Test
    void findByUsername() {
        assertTrue(userProfileRepository.findByUsername("EshikaLove").isPresent());
    }

    @Test
    void update() {
        userProfileDoc.setUsername("EskikaUpdated");
        userProfileDoc.setMobile("9999999999");
        userProfileRepository.save(userProfileDoc);
        Optional<UserProfileDoc> userProfileDocOptional = userProfileRepository.findById(userProfileDoc.getId());
        assertTrue(userProfileDocOptional.isPresent());
        assertEquals("EskikaUpdated", userProfileDocOptional.get().getUsername());
        assertEquals("9999999999", userProfileDocOptional.get().getMobile());
    }
}