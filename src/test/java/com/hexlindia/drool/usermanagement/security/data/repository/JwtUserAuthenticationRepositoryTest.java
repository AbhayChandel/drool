package com.hexlindia.drool.usermanagement.security.data.repository;

import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class JwtUserAuthenticationRepositoryTest {

    @Autowired
    UserAuthenticationRepository userAuthenticationRepository;

    @Test
    private void testRetrievingUserAuthenticationEntityById() {
        Optional<UserAuthenticationEntity> userAuthenticationEntity = userAuthenticationRepository.findById(1L);
        assertTrue(userAuthenticationEntity.isPresent());
    }

    @Test
    private void testRetrievingUserAuthenticationEntityByEmail() {
        Optional<UserAuthenticationEntity> userAuthenticationEntity = userAuthenticationRepository.findByEmail("talk_to_priyanka@gmail.com");
        assertTrue(userAuthenticationEntity.isPresent());
    }
}