package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.entity.UserAccountVerificationEntity;
import com.hexlindia.drool.user.data.entity.UserAccountVerificationId;
import com.hexlindia.drool.user.data.entity.VerificationTypeEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.data.repository.api.UserVerificationRepository;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.dto.mapper.UserAccountMapper;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import com.hexlindia.drool.user.services.AuthenticatedUserDetails;
import com.hexlindia.drool.user.services.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserAccountImpl implements UserAccount {

    private final UserAccountRepository userAccountRepository;

    private final UserVerificationRepository userVerificationRepository;

    private final JwtUtil jwtUtil;

    private final UserProfile userProfile;

    private final PasswordEncoder passwordEncoder;

    private final UserAccountMapper userAccountMapper;

    @Override
    @Transactional
    public JwtResponse register(UserRegistrationDto userRegistrationDto) {
        UserAccountEntity userAccountEntity = userAccountRepository.saveAndFlush(getUserAuthenticationEntity(userRegistrationDto));
        addVerification(userAccountEntity.getId());
        UserProfileDto userProfileDto = userRegistrationDto.getUserProfileDto();
        userProfileDto.setId(String.valueOf(userAccountEntity.getId()));
        userProfile.create(userProfileDto);
        return new JwtResponse(jwtUtil.generateToken(userAccountEntity.getEmail()), new AuthenticatedUserDetails(String.valueOf(userAccountEntity.getId()), userAccountEntity.getUsername()));
    }

    @Override
    public UserAccountDto findUser(String userIdentifier) {
        Optional<UserAccountEntity> userAccountEntityOptional = this.userAccountRepository.findUser(userIdentifier);
        if (userAccountEntityOptional.isPresent()) {
            log.info("user with email/username {} found", userIdentifier);
            return userAccountMapper.toDto(userAccountEntityOptional.get());
        }
        throw new UserAccountNotFoundException("User Account with Email/Username " + userIdentifier + " not found");
    }

    UserAccountEntity getUserAuthenticationEntity(UserRegistrationDto userRegistrationDto) {
        UserAccountEntity userAuthenticationEntity = this.userAccountMapper.toEntity(userRegistrationDto.getUserAccountDto());
        setEncodedPasswordInEntity(userAuthenticationEntity);
        return userAuthenticationEntity;
    }

    void setEncodedPasswordInEntity(UserAccountEntity userAuthenticationEntity) {
        userAuthenticationEntity.setPassword(getEncodedPassword(userAuthenticationEntity.getPassword()));
    }

    String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private void addVerification(long userId) {
        userVerificationRepository.save(getVerificationEntity(userId, "email"));
        userVerificationRepository.save(getVerificationEntity(userId, "mobile"));
    }

    private UserAccountVerificationEntity getVerificationEntity(Long userId, String verificationType) {
        UserAccountVerificationEntity userAccountVerificationEntity = new UserAccountVerificationEntity();
        UserAccountVerificationId userAccountVerificationId = new UserAccountVerificationId();
        VerificationTypeEntity verificationTypeEntity = null;
        if (verificationType.equalsIgnoreCase("email")) {
            verificationTypeEntity = new VerificationTypeEntity(1, "Email");
        } else if (verificationType.equalsIgnoreCase("mobile")) {
            verificationTypeEntity = new VerificationTypeEntity(2, "Mobile");
        }
        userAccountVerificationId.setVerificationType(verificationTypeEntity);
        userAccountVerificationId.setUserId(userId);
        userAccountVerificationEntity.setId(userAccountVerificationId);
        return userAccountVerificationEntity;
    }
}
