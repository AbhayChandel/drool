package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.api.usecase.UserVerification;
import com.hexlindia.drool.user.constant.VerificationType;
import com.hexlindia.drool.user.data.repository.api.UserVerificationRepository;
import com.hexlindia.drool.user.dto.UserVerificationDto;
import com.hexlindia.drool.user.dto.mapper.UserVerificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserVerificationImpl implements UserVerification {

    private final UserVerificationRepository userVerificationRepository;
    private final UserVerificationMapper userVerificationMapper;

    @Override
    public List<UserVerificationDto> findAll(String userId) {
        return userVerificationMapper.toDtoList(userVerificationRepository.findByIdUserId(Long.valueOf(userId)));
    }

    @Override
    public UserVerificationDto findVerification(String userId, VerificationType verificationType) {
        return userVerificationMapper.toDto(userVerificationRepository.findByIdUserIdAndIdVerificationTypeId(Long.valueOf(userId), getVerificationType(verificationType)));
    }

    int getVerificationType(VerificationType verificationType) {
        switch (verificationType) {
            case email:
                return 1;
            case mobile:
                return 2;
            default:
                return 0;
        }
    }
}
