package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.dto.UserProfileDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@RequestMapping("/${rest.uri.version}/user/profile")
public interface UserProfileRestService {

    @PutMapping(value = "/${rest.uri.version}/user/profile/update")
    ResponseEntity<UserProfileDto> update(@RequestBody UserProfileDto userProfileDto);

    @GetMapping(value = "/${rest.uri.version}/user/profile/find/id/{id}")
    ResponseEntity<UserProfileDto> findById(@PathVariable("id") String id);

    @GetMapping(value = "/${rest.uri.version}/accessall/user/profile/find/username/{username}")
    ResponseEntity<UserProfileDto> findByUsername(@PathVariable("username") String username);
}
