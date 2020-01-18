package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/${rest.uri.version}/user/profile")
public interface UserProfileRestService {

    @PutMapping(value = "/${rest.uri.version}/user/profile/update")
    ResponseEntity<UserProfileTo> update(@RequestBody UserProfileTo userProfileTo);

    @GetMapping(value = "/${rest.uri.version}/user/profile/find/id/{id}")
    ResponseEntity<UserProfileTo> findById(@PathVariable("id") Long id);

    @GetMapping(value = "/${rest.uri.version}/accessall/user/profile/find/username/{username}")
    ResponseEntity<UserProfileTo> findByUsername(@PathVariable("username") String username);
}
