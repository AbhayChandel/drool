package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.user.services.JwtResponse;

public interface UserAccount {

    JwtResponse register(UserRegistrationDetailsTo userAuthenticationEntity);

    UserAccountTo findByEmail(String email);
}
