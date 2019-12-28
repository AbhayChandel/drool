package com.hexlindia.drool.user.business.api.to.mapper;

import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegistrationToUserProfileMapperTest {

    private final RegistrationToUserProfileMapper mapper = Mappers.getMapper(RegistrationToUserProfileMapper.class);

    @Test
    void toUserProfileTo() {
        UserRegistrationDetailsTo userRegistrationDetailsTo = new UserRegistrationDetailsTo("kriti99", "kriti99@gmail.com", "kriti");
        UserProfileTo userProfileTo = mapper.toUserProfileTo(userRegistrationDetailsTo);
        assertEquals("kriti99", userProfileTo.getUsername());
    }
}