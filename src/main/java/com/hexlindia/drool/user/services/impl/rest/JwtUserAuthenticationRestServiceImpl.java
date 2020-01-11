package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.user.services.JwtRequest;
import com.hexlindia.drool.user.services.JwtResponse;
import com.hexlindia.drool.user.services.api.rest.JwtUserAuthenticationRestService;
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
        return ResponseEntity.ok(jwtUserAuthentication.authenticate(jwtRequest.getEmail(), jwtRequest.getPassword()));
    }
}
