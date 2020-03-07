package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.business.api.to.ContributionSummaryDto;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;

public interface UserProfile {

    UserProfileTo create(UserProfileTo userProfileTo);

    UserProfileTo findById(Long userAccountId);

    UserProfileTo findByUsername(String username);

    UserProfileTo update(UserProfileTo userProfileTo);

    ContributionSummaryDto getContributionSummary(String userId);
}
