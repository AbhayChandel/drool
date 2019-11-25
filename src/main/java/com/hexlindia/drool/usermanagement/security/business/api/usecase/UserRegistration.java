package com.hexlindia.drool.usermanagement.security.business.api.usecase;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;

public interface UserRegistration {

    String register(UserRegistrationDetailsTo userAuthenticationEntity);
}
