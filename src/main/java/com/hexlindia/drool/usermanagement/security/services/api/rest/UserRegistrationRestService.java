package com.hexlindia.drool.usermanagement.security.services.api.rest;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/${rest.uri.version}/user")
public interface UserRegistrationRestService {

    @PostMapping(value = "/register")
    ResponseEntity<JwtResponse> register(@RequestBody UserRegistrationDetailsTo userRegistrationDetailsTo);

    /*@GetMapping(value="")
    boolean isUsernameAvailable(String username);

    @GetMapping(value="")
    boolean isEmailAlreadyRegistered(String email);*/
}
