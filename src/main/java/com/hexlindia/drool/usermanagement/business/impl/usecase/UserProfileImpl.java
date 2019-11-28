package com.hexlindia.drool.usermanagement.business.impl.usecase;

import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserProfileMapper;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserProfile;
import com.hexlindia.drool.usermanagement.data.entity.UserProfileEntity;
import com.hexlindia.drool.usermanagement.data.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
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
            UserProfileEntity userProfileEntity = userProfileEntityOptional.get();
            return userProfileMapper.toTransferObject(userProfileEntity);
        }
        return new UserProfileTo();
    }

    @Override
    public UserProfileTo update(UserProfileTo userProfileTo) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileTo));
        return this.userProfileMapper.toTransferObject(userProfileEntity);
    }
}
