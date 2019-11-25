package com.hexlindia.drool.usermanagement.security.services.impl.rest;

import com.hexlindia.drool.usermanagement.security.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.security.services.JwtRequest;
import com.hexlindia.drool.usermanagement.security.services.JwtResponse;
import com.hexlindia.drool.usermanagement.security.services.api.rest.JwtUserAuthenticationRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtUserAuthenticationRestServiceImpl implements JwtUserAuthenticationRestService {

    private final JwtUserAuthentication jwtUserAuthentication;

    @Autowired
    JwtUserAuthenticationRestServiceImpl(JwtUserAuthentication jwtUserAuthentication) {
        this.jwtUserAuthentication = jwtUserAuthentication;
    }

    @Override
    public ResponseEntity<JwtResponse> createAuthenticationToken(JwtRequest jwtRequest) {
        return ResponseEntity.ok(new JwtResponse(jwtUserAuthentication.authenticate(jwtRequest.getEmail(), jwtRequest.getPassword())));
    }
}
