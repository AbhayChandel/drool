package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.services.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/accessall/user/account")
public interface UserAccountRestService {

    @PostMapping(value = "/register")
    ResponseEntity<JwtResponse> register(@RequestBody UserRegistrationDto userRegistrationDto);

    @GetMapping(value = "/find/{userIdentifier}")
    ResponseEntity<UserAccountDto> findByEmail(@PathVariable("userIdentifier") String userIdentifier);


}
