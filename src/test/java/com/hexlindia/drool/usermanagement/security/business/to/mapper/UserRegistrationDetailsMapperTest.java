package com.hexlindia.drool.usermanagement.security.business.to.mapper;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRegistrationDetailsMapperTest {

    private final UserRegistrationDetailsMapper mapper = Mappers.getMapper(UserRegistrationDetailsMapper.class);

    @Test
    void testMappingTOToEntity() {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kriti99", "kriti99@gmail.com", "kriti");
        UserAuthenticationEntity userAuthenticationEntity = mapper.toEntity(userRegistrationDetailsTo);
        assertEquals("kriti99@gmail.com", userAuthenticationEntity.getEmail());
        assertEquals("kriti", userAuthenticationEntity.getPassword());
    }
}
