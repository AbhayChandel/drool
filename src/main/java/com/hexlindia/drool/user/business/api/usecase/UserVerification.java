package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.constant.VerificationType;
import com.hexlindia.drool.user.dto.UserVerificationDto;

import java.util.List;

public interface UserVerification {

    List<UserVerificationDto> findAll(String userId);

    UserVerificationDto findVerification(String userId, VerificationType verificationType);
}
