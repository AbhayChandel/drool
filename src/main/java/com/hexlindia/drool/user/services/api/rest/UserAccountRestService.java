package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.user.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/user/account")
public interface UserAccountRestService {

    @PostMapping(value = "/register")
    ResponseEntity<JwtResponse> register(@RequestBody UserRegistrationDetailsTo userRegistrationDetailsTo);

    @GetMapping(value = "/find/email/{email}")
    ResponseEntity<UserAccountTo> findByEmail(@PathVariable("email") String email);


}
