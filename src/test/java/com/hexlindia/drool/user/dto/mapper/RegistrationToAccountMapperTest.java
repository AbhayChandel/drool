package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.dto.UserRegistrationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RegistrationToAccountMapperTest {

    @Autowired
    RegistrationToAccountMapper registrationToAccountMapper;

    @Test
    void toEntity() {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();

    }
}