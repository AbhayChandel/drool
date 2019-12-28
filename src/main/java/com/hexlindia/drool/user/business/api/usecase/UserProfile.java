package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.business.api.to.UserProfileTo;

public interface UserProfile {

    UserProfileTo create(UserProfileTo userProfileTo);

    UserProfileTo findByUsername(String username);

    UserProfileTo update(UserProfileTo userProfileTo);
}
