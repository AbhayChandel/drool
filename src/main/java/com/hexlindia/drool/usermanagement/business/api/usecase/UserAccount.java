package com.hexlindia.drool.usermanagement.business.api.usecase;

import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;

public interface UserAccount {

    String register(UserRegistrationDetailsTo userAuthenticationEntity);

    UserAccountTo findByEmail(String email);
}
