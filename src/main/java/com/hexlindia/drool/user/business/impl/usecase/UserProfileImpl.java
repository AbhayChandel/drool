package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.to.mapper.UserProfileMapper;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.data.repository.UserProfileRepository;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserProfileImpl implements UserProfile {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    @Autowired
    UserProfileImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public UserProfileTo create(UserProfileTo userProfileTo) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileTo));
        return this.userProfileMapper.toTransferObject(userProfileEntity);
    }


    @Override
    public UserProfileTo findByUsername(String username) {
        Optional<UserProfileEntity> userProfileEntityOptional = this.userProfileRepository.findByUsername(username);
        if (userProfileEntityOptional.isPresent()) {
            log.info("User with username {} found", username);
            UserProfileEntity userProfileEntity = userProfileEntityOptional.get();
            return userProfileMapper.toTransferObject(userProfileEntity);
        }
        throw new UserProfileNotFoundException("User profile with username " + username + " not found");
    }

    @Override
    public UserProfileTo update(UserProfileTo userProfileTo) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileTo));
        log.debug("User profile after update {}", userProfileEntity);
        return this.userProfileMapper.toTransferObject(userProfileEntity);
    }
}
