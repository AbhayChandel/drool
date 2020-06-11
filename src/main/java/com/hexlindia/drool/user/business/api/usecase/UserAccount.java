package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.services.JwtResponse;

public interface UserAccount {

    JwtResponse register(UserRegistrationDto userRegistrationDto);

    UserAccountDto findUser(String userIdentifier);
}
