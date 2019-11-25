package com.hexlindia.drool.usermanagement.security.services.impl.rest;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.UserRegistration;
import com.hexlindia.drool.usermanagement.security.services.JwtResponse;
import com.hexlindia.drool.usermanagement.security.services.api.rest.UserRegistrationRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserRegistrationRestServiceImpl implements UserRegistrationRestService {

    private final UserRegistration userRegistration;

    @Autowired
    public UserRegistrationRestServiceImpl(UserRegistration userRegistration) {
        this.userRegistration = userRegistration;
    }

    @Override
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody UserRegistrationDetailsTo userRegistrationDetailsTo) {
        return ResponseEntity.ok(new JwtResponse(userRegistration.register(userRegistrationDetailsTo)));
    }
}
