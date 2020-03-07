package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.api.to.ContributionSummaryDto;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.to.mapper.UserProfileMapper;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.data.repository.UserProfileRepository;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.video.business.api.usecase.Video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class UserProfileImpl implements UserProfile {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    private final Video video;

    @Autowired
    UserProfileImpl(UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper, Video video) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
        this.video = video;
    }

    @Override
    public UserProfileTo create(UserProfileTo userProfileTo) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileTo));
        return this.userProfileMapper.toTransferObject(userProfileEntity);
    }

    @Override
    public UserProfileTo findById(Long userAccountId) {
        Optional<UserProfileEntity> userProfileEntityOptional = this.userProfileRepository.findById(userAccountId);
        if (userProfileEntityOptional.isPresent()) {
            log.debug("User with id {} found", userAccountId);
            return userProfileMapper.toTransferObject(userProfileEntityOptional.get());
        }
        throw new UserProfileNotFoundException("User profile with id " + userAccountId + " not found");
    }


    @Override
    public UserProfileTo findByUsername(String username) {
        Optional<UserProfileEntity> userProfileEntityOptional = this.userProfileRepository.findByUsername(username);
        if (userProfileEntityOptional.isPresent()) {
            log.debug("User with username {} found", username);
            return userProfileMapper.toTransferObject(userProfileEntityOptional.get());
        }
        throw new UserProfileNotFoundException("User profile with username " + username + " not found");
    }

    @Override
    public UserProfileTo update(UserProfileTo userProfileTo) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileTo));
        log.debug("User profile after update {}", userProfileEntity);
        return this.userProfileMapper.toTransferObject(userProfileEntity);
    }

    @Override
    public ContributionSummaryDto getContributionSummary(String userId) {
        return new ContributionSummaryDto(video.getLatestThreeVideoThumbnails(userId));
    }
}
