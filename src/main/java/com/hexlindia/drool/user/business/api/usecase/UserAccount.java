package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;

public interface UserAccount {

    String register(UserRegistrationDetailsTo userAuthenticationEntity);

    UserAccountTo findByEmail(String email);
}
