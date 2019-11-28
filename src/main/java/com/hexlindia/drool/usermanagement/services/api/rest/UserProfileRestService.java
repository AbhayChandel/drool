package com.hexlindia.drool.usermanagement.services.api.rest;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/${rest.uri.version}/user/profile")
public interface UserProfileRestService {

    @PutMapping(value = "/update")
    ResponseEntity<UserProfileTo> update(@RequestBody UserProfileTo userProfileTo);

    @GetMapping(value = "/find/username/{username}")
    ResponseEntity<UserProfileTo> findByUsername(@PathVariable("username") String username);
}
