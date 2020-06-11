package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.services.api.rest.UserProfileRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileRestServiceImpl implements UserProfileRestService {

    private final UserProfile userProfile;

    @Autowired
    UserProfileRestServiceImpl(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public ResponseEntity<UserProfileDto> update(UserProfileDto userProfileDto) {
        return ResponseEntity.ok(this.userProfile.update(userProfileDto));
    }

    @Override
    public ResponseEntity<UserProfileDto> findById(String id) {
        return ResponseEntity.ok(this.userProfile.findById(id));
    }
}
