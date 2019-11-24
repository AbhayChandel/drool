package com.hexlindia.drool.usermanagement.security.jwt.services.api.rest;

import com.hexlindia.drool.usermanagement.security.jwt.services.JwtRequest;
import com.hexlindia.drool.usermanagement.security.jwt.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public interface JwtUserAuthenticationRestService {

    @PostMapping(value = "/authenticate")
    ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest);
}
