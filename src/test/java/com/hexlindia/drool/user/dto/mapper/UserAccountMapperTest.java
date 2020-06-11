package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.dto.UserAccountDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserAccountMapperTest {

    @Autowired
    UserAccountMapper userAccountMapper;

    @Test
    void toDto() {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(1001L);
        userAccountEntity.setEmail("priya11@gmail.com");
        userAccountEntity.setPassword("$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi");
        userAccountEntity.setUsername("priya11");
        UserAccountDto userAccountDto = userAccountMapper.toDto(userAccountEntity);
        assertEquals("priya11@gmail.com", userAccountDto.getEmailId());
        assertEquals("$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi", userAccountDto.getPassword());
        assertEquals("priya11", userAccountDto.getUsername());
    }

    @Test
    void toEntity() {
        UserAccountDto userAccountDto = new UserAccountDto();
        userAccountDto.setEmailId("priya99@gmail.com");
        userAccountDto.setPassword("abcd");
        userAccountDto.setUsername("priya99");
        UserAccountEntity userAccountEntity = userAccountMapper.toEntity(userAccountDto);
        assertEquals("priya99@gmail.com", userAccountEntity.getEmail());
        assertEquals("abcd", userAccountEntity.getPassword());
        assertEquals("priya99", userAccountEntity.getUsername());
    }
}