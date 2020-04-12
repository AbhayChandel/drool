package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.dto.ContributionSummaryDto;
import com.hexlindia.drool.user.dto.UserProfileDto;

public interface UserProfile {

    UserProfileDto create(UserProfileDto userProfileDto);

    UserProfileDto findById(String id);

    UserProfileDto findByUsername(String username);

    UserProfileDto update(UserProfileDto userProfileDto);

    ContributionSummaryDto getContributionSummary(String userId);
}
