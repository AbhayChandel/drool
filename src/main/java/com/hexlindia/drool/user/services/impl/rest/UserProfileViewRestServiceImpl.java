package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.to.ContributionSummaryDto;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.services.api.rest.UserProfileViewRestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileViewRestServiceImpl implements UserProfileViewRestService {

    private final UserProfile userProfile;

    public UserProfileViewRestServiceImpl(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public ResponseEntity<ContributionSummaryDto> getContributionSummary(String userId) {
        return ResponseEntity.ok(this.userProfile.getContributionSummary(userId));
    }
}
