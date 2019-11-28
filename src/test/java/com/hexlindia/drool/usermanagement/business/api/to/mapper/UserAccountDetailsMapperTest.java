package com.hexlindia.drool.usermanagement.business.api.to.mapper;

import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserAccountDetailsMapperTest {

    private final UserRegistrationDetailsMapper mapper = Mappers.getMapper(UserRegistrationDetailsMapper.class);

    @Test
    void testMappingTOToEntity() {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kriti99", "kriti99@gmail.com", "kriti");
        UserAccountEntity userAuthenticationEntity = mapper.toEntity(userRegistrationDetailsTo);
        assertEquals("kriti99@gmail.com", userAuthenticationEntity.getEmail());
        assertEquals("kriti", userAuthenticationEntity.getPassword());
    }
}
