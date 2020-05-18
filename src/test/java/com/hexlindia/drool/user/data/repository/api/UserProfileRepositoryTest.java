package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserProfileRepositoryTest {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Test
    void testRetrievingUserProfileEntityById() {
        Optional<UserProfileEntity> userProfileEntityOptional = this.userProfileRepository.findById(1L);
        assertTrue(userProfileEntityOptional.isPresent());
        UserProfileEntity userProfileEntity = userProfileEntityOptional.get();
        assertEquals("Priyanka Singh", userProfileEntity.getName());
        assertEquals("Indore", userProfileEntity.getCity());
        assertEquals("F", userProfileEntity.getGender());
        assertNotNull(userProfileEntity.getJoinDate());
    }

    @Test
    void testUpdatingUserProfile() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity();
        userProfileEntityMocked.setCity("Indore");
        userProfileEntityMocked.setGender("F");
        userProfileEntityMocked.setId(2L);
        UserProfileEntity userProfileToReturned = this.userProfileRepository.save(userProfileEntityMocked);
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("Indore", userProfileToReturned.getCity());
        assertEquals("F", userProfileToReturned.getGender());
    }
}