package com.hexlindia.drool.user.dto;

import com.hexlindia.drool.user.constant.VerificationType;
import lombok.Data;

@Data
public class UserVerificationDto {
    private String userId;
    private VerificationType verificationType;
    private boolean verified;
}
