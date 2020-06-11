package com.hexlindia.drool.user.data.repository.api;

import com.hexlindia.drool.user.data.entity.UserAccountVerificationEntity;
import com.hexlindia.drool.user.data.entity.UserAccountVerificationId;
import com.hexlindia.drool.user.data.entity.VerificationTypeEntity;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserVerificationRepositoryTest {

    @Autowired
    UserVerificationRepository userVerificationRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void save() {
        //VerificationTypeEntity verificationTypeEntity = entityManager.find(VerificationTypeEntity.class, 1);
        VerificationTypeEntity verificationTypeEntity = new VerificationTypeEntity();
        verificationTypeEntity.setId(1);
        verificationTypeEntity.setName("Email");
        UserAccountVerificationId id = new UserAccountVerificationId(2, verificationTypeEntity);
        UserAccountVerificationEntity userAccountVerificationEntity = new UserAccountVerificationEntity();
        userAccountVerificationEntity.setId(id);
        entityManager.persist(userAccountVerificationEntity);
        userAccountVerificationEntity = null;
        userAccountVerificationEntity = entityManager.find(UserAccountVerificationEntity.class, id);
        assertEquals(2, userAccountVerificationEntity.getId().getUserId());
    }

    @Test
    @Disabled
    void saveDuplicateId() {
        VerificationTypeEntity verificationTypeEntity = entityManager.find(VerificationTypeEntity.class, 1);
        UserAccountVerificationId id = new UserAccountVerificationId(2, verificationTypeEntity);
        UserAccountVerificationEntity userAccountVerificationEntity = new UserAccountVerificationEntity();
        userAccountVerificationEntity.setId(id);
        UserAccountVerificationId idDuplicate = new UserAccountVerificationId(2, verificationTypeEntity);
        UserAccountVerificationEntity userAccountVerificationEntityDuplicate = new UserAccountVerificationEntity();
        userAccountVerificationEntityDuplicate.setId(idDuplicate);

        assertTrue(id.equals(idDuplicate));

        assertThrows(DataAccessException.class, () -> {
            entityManager.persist(userAccountVerificationEntityDuplicate);
        });
    }

    @Test
    void findAllVerification() {
        List<UserAccountVerificationEntity> userAccountVerificationEntityList = userVerificationRepository.findByIdUserId(1L);
        assertEquals(2, userAccountVerificationEntityList.size());
    }

    @Test
    void findByUserIdAndVerificationType() {
        UserAccountVerificationEntity userAccountVerificationEntity = userVerificationRepository.findByIdUserIdAndIdVerificationTypeId(1L, 2);
        assertEquals(1L, userAccountVerificationEntity.getId().getUserId());
        assertEquals(2, userAccountVerificationEntity.getId().getVerificationType().getId());
    }

}