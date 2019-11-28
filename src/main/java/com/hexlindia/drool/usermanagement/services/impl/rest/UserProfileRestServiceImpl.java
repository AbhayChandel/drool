package com.hexlindia.drool.usermanagement.services.impl.rest;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserProfile;
import com.hexlindia.drool.usermanagement.services.api.rest.UserProfileRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileRestServiceImpl implements UserProfileRestService {

    @Autowired
    private UserProfile userProfile;

    @Override
    public ResponseEntity<UserProfileTo> update(UserProfileTo userProfileTo) {
        return ResponseEntity.ok(this.userProfile.update(userProfileTo));
    }

    @Override
    public ResponseEntity<UserProfileTo> findByUsername(String username) {
        return ResponseEntity.ok(this.userProfile.findByUsername(username));
    }
}
