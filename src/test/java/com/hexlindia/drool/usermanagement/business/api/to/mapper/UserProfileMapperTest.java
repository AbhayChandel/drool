package com.hexlindia.drool.usermanagement.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.data.entity.UserProfileEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserProfileMapperTest {

    private final UserProfileMapper mapper = Mappers.getMapper(UserProfileMapper.class);

    @Test
    void testMappingTOToEntity() {
        UserProfileTo userProfileTo = new UserProfileTo(1, "kritiG", 9876543210L, "Pune", 'F');
        UserProfileEntity userProfileEntity = mapper.toEntity(userProfileTo);
        assertEquals(1, userProfileEntity.getId());
        assertEquals("kritiG", userProfileEntity.getUsername());
        assertEquals(9876543210L, userProfileEntity.getMobile());
        assertEquals("Pune", userProfileEntity.getCity());
        assertEquals('F', userProfileEntity.getGender());
    }

    @Test
    void testMappingEntityToTransferObject() {
        UserProfileEntity userProfileEntity = new UserProfileEntity();
        userProfileEntity.setId(2L);
        userProfileEntity.setUsername("priya21");
        userProfileEntity.setMobile(8765432109L);
        userProfileEntity.setCity("Pune");
        userProfileEntity.setGender('F');
        UserProfileTo userProfileTo = mapper.toTransferObject(userProfileEntity);
        assertEquals(2, userProfileTo.getId());
        assertEquals("priya21", userProfileTo.getUsername());
        assertEquals(8765432109L, userProfileTo.getMobile());
        assertEquals("Pune", userProfileTo.getCity());
        assertEquals('F', userProfileTo.getGender());
    }
}