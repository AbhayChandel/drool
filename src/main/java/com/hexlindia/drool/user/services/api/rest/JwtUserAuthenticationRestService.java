package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.services.JwtRequest;
import com.hexlindia.drool.user.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/accessall/user/account")
public interface JwtUserAuthenticationRestService {

    @PostMapping(value = "/authenticate")
    ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest jwtRequest);
}
