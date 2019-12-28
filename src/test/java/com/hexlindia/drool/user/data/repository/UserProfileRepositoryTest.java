package com.hexlindia.drool.user.data.repository;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UserProfileRepositoryTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void testRetrievingUserProfileEntityById() {
        Optional<UserProfileEntity> userProfileEntity = this.userProfileRepository.findById(1L);
        assertTrue(userProfileEntity.isPresent());
    }

    @Test
    void testRetrievingUserProfileEntityByEmail() {
        Optional<UserProfileEntity> userProfileEntity = this.userProfileRepository.findByUsername("sonam31");
        assertTrue(userProfileEntity.isPresent());
    }

    @Test
    void testUpdatingUserProfile() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity("kriti99", 9876543210L, "Indore", 'F');
        userProfileEntityMocked.setId(2L);
        UserProfileEntity userProfileToReturned = this.userProfileRepository.save(userProfileEntityMocked);
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("kriti99", userProfileToReturned.getUsername());
        assertEquals(9876543210L, userProfileToReturned.getMobile());
        assertEquals("Indore", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }
}