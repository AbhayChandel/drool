package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBConfig;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(MongoDBConfig.class)
class UserAccountRepositoryImplTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();
        userAccountDocPriyanka.setEmailId("priyanka.singh@gmail.com");
        userAccountDocPriyanka.setPassword("priyanka");
        userAccountDocPriyanka.setActive(true);
        mongoOperations.save(userAccountDocPriyanka);

        UserAccountDoc userAccountDocSonam = new UserAccountDoc();
        userAccountDocSonam.setEmailId("sonam99@gmail.com");
        userAccountDocSonam.setPassword("sonam");
        mongoOperations.save(userAccountDocSonam);
    }

    @Test
    void save() {
        UserAccountDoc userAccountDocSonal = new UserAccountDoc();
        userAccountDocSonal.setEmailId("sonal.singh@gmail.com");
        userAccountDocSonal.setPassword("sonal");
        userAccountDocSonal.setActive(true);
        assertNotNull(userAccountRepository.save(userAccountDocSonal));

    }

    @Test
    void findByEmailActiveAccount() {
        assertTrue(userAccountRepository.findByEmail("priyanka.singh@gmail.com").isPresent());
    }

    @Test
    void findByEmailInactiveAccount() {
        assertFalse(userAccountRepository.findByEmail("sonam99@gmail.com").isPresent());
    }
}