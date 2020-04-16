package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
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

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor

public class UserAccountImpl implements UserAccount {

    private final UserAccountRepository userAccountRepository;

    private final JwtUtil jwtUtil;

    private final UserProfile userProfile;

    private final PasswordEncoder passwordEncoder;

    private final UserAccountMapper userAccountMapper;


    @Override
    public JwtResponse register(UserRegistrationDto userRegistrationDto) {
        UserAccountDoc userAccountDoc = userAccountMapper.toDoc(userRegistrationDto.getUserAccountDto());
        setEncodedPassword(userAccountDoc);
        userAccountDoc.setUsername(userRegistrationDto.getUserProfileDto().getUsername());
        userAccountDoc.setActive(true);
        userAccountDoc = userAccountRepository.save(userAccountDoc);

        UserProfileDto userProfileDto = userRegistrationDto.getUserProfileDto();
        userProfileDto.setId(userAccountDoc.getId().toHexString());
        userProfileDto = userProfile.create(userProfileDto);
        return new JwtResponse(jwtUtil.generateToken(userAccountDoc.getEmailId()), new AuthenticatedUserDetails(userProfileDto.getId(), userProfileDto.getUsername()));
    }

    @Override
    public UserAccountDto findByEmail(String email) {
        Optional<UserAccountDoc> userAccountDocOptional = this.userAccountRepository.findByEmail(email);
        if (userAccountDocOptional.isPresent()) {
            log.info("user with email {} found", email);
            return userAccountMapper.toDto(userAccountDocOptional.get());
        }
        throw new UserAccountNotFoundException("User Account with email " + email + " not found");
    }

    void setEncodedPassword(UserAccountDoc userAccountDoc) {
        userAccountDoc.setPassword(getEncodedPassword(userAccountDoc.getPassword()));
    }

    String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
