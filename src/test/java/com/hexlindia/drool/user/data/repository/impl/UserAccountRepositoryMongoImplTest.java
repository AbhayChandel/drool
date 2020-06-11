package com.hexlindia.drool.user.data.repository.impl;

import com.hexlindia.drool.common.config.MongoDBTestConfig;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepositoryMongo;
import com.hexlindia.drool.user.exception.EmailExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(MongoDBTestConfig.class)
class UserAccountRepositoryMongoImplTest {

    @Autowired
    UserAccountRepositoryMongo userAccountRepository;

    @Autowired
    MongoOperations mongoOperations;

    @BeforeEach
    void setUp() {
        UserAccountDoc userAccountDocPriyanka = new UserAccountDoc();
        userAccountDocPriyanka.setEmailId("priyanka.singh@gmail.com");
        userAccountDocPriyanka.setPassword("priyanka");
        userAccountDocPriyanka.setUsername("priyanka11");
        userAccountDocPriyanka.setActive(true);
        mongoOperations.save(userAccountDocPriyanka);

        UserAccountDoc userAccountDocSonam = new UserAccountDoc();
        userAccountDocSonam.setEmailId("sonam99@gmail.com");
        userAccountDocSonam.setPassword("sonam");
        mongoOperations.save(userAccountDocSonam);
    }


    @Test
    void saveEmailExistException() {
        UserAccountDoc userAccountDocSonal = new UserAccountDoc();
        userAccountDocSonal.setEmailId("priyanka.singh@gmail.com");
        userAccountDocSonal.setPassword("sonal");
        userAccountDocSonal.setActive(true);
        Assertions.assertThrows(EmailExistException.class, () -> {
            userAccountRepository.save(userAccountDocSonal);
        });

    }

    @Test
    void saveSuccessfully() {
        UserAccountDoc userAccountDocSonal = new UserAccountDoc();
        userAccountDocSonal.setEmailId("sonal.singh@gmail.com");
        userAccountDocSonal.setPassword("sonal");
        userAccountDocSonal.setActive(true);
        assertNotNull(userAccountRepository.save(userAccountDocSonal));
    }

    @Test
    void findByEmailActiveAccount() {
        Optional<UserAccountDoc> userAccountDocOptional = userAccountRepository.findByEmail("priyanka.singh@gmail.com");
        assertTrue(userAccountDocOptional.isPresent());
        UserAccountDoc userAccountDoc = userAccountDocOptional.get();
        assertEquals("priyanka.singh@gmail.com", userAccountDoc.getEmailId());
        assertEquals("priyanka", userAccountDoc.getPassword());
        assertEquals("priyanka11", userAccountDoc.getUsername());
        assertTrue(userAccountDoc.isActive());
    }

    @Test
    void findByEmailInactiveAccount() {
        assertFalse(userAccountRepository.findByEmail("sonam99@gmail.com").isPresent());
    }
}