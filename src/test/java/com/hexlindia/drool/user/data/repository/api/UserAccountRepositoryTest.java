package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserAccountRepositoryTest {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Test
    void findById() {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findById(1L);
        assertTrue(userAuthenticationEntity.isPresent());
        UserAccountEntity userAccountEntity = userAuthenticationEntity.get();
        assertEquals("talk_to_priyanka@gmail.com", userAccountEntity.getEmail());
        assertEquals("Priyankalove", userAccountEntity.getUsername());
        assertEquals("9876543210", userAccountEntity.getMobile());
        assertTrue(userAccountEntity.isActive());
    }

    @Test
    void findByEmail() {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findUser("talk_to_priyanka@gmail.com");
        assertTrue(userAuthenticationEntity.isPresent());
    }

    @Test
    void findByUsername() {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findUser("Priyankalove");
        assertTrue(userAuthenticationEntity.isPresent());
    }
}