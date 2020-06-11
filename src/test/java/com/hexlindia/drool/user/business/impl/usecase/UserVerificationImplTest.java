package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.constant.VerificationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserVerificationImplTest {

    @Autowired
    UserVerificationImpl userAccountVerification;

    @Test
    void getVerificationType() {
        assertEquals(1, userAccountVerification.getVerificationType(VerificationType.email));
        assertEquals(2, userAccountVerification.getVerificationType(VerificationType.mobile));
    }
}