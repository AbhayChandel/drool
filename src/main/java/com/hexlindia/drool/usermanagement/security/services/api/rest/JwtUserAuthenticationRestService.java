package com.hexlindia.drool.usermanagement.security.services.api.rest;

import com.hexlindia.drool.usermanagement.security.services.JwtRequest;
import com.hexlindia.drool.usermanagement.security.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/v1")
public interface JwtUserAuthenticationRestService {

    @PostMapping(value = "/authenticate")
    ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest);
}
