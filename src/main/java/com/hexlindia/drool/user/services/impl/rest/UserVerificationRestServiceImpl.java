package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.usecase.UserVerification;
import com.hexlindia.drool.user.constant.VerificationType;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import com.hexlindia.drool.user.services.api.rest.UserVerificationRestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserVerificationRestServiceImpl implements UserVerificationRestService {

    private final UserVerification userVerification;

    @Override
    public ResponseEntity<List<UserVerificationDto>> findAll(String userId) {
        return ResponseEntity.ok(userVerification.findAll(userId));
    }

    @Override
    public ResponseEntity<UserVerificationDto> findVerification(String userId, VerificationType verificationType) {
        return ResponseEntity.ok(userVerification.findVerification(userId, verificationType));
    }
}
