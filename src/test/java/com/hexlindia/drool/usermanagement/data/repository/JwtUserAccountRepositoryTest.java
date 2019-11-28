package com.hexlindia.drool.usermanagement.data.repository;

import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class JwtUserAccountRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Test
    void testRetrievingUserAuthenticationEntityById() {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findById(1L);
        assertTrue(userAuthenticationEntity.isPresent());
    }

    @Test
    void testRetrievingUserAuthenticationEntityByEmail() {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findByEmail("talk_to_priyanka@gmail.com");
        assertTrue(userAuthenticationEntity.isPresent());
    }
}