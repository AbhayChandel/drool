package com.hexlindia.drool.user.services.api.rest;

import com.hexlindia.drool.user.constant.VerificationType;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/${rest.uri.version}/user/verification")
public interface UserVerificationRestService {

    @GetMapping(value = "/find/all/{userId}")
    ResponseEntity<List<UserVerificationDto>> findAll(@PathVariable("userId") String userId);

    @GetMapping(value = "/find/{userId}/{verificationType}")
    ResponseEntity<UserVerificationDto> findVerification(@PathVariable("userId") String userId, @PathVariable("verificationType") VerificationType verificationType);


}
