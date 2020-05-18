package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.dto.UserProfileDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserProfileMapperTest {

    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    void toEntity() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId("10009");
        userProfileDto.setName("Panna Kashyap");
        userProfileDto.setCity("Pune");
        userProfileDto.setGender("F");
        UserProfileEntity userProfileEntity = userProfileMapper.toEntity(userProfileDto);
        assertEquals(10009L, userProfileEntity.getId());
        assertEquals("Panna Kashyap", userProfileEntity.getName());
        assertEquals("Pune", userProfileEntity.getCity());
        assertEquals("F", userProfileEntity.getGender());
    }

    @Test
    void toDto() {
        UserProfileEntity userProfileEntity = new UserProfileEntity();

        userProfileEntity.setCity("Indore");
        userProfileEntity.setGender("F");
        userProfileEntity.setId(1001L);
        UserProfileDto userProfileDto = userProfileMapper.toDto(userProfileEntity);
        assertEquals("1001", userProfileDto.getId());
        assertEquals("Indore", userProfileDto.getCity());
        assertEquals("F", userProfileDto.getGender());
    }
}