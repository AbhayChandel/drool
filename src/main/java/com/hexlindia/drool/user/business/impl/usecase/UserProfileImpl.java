package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.data.repository.api.UserProfileRepository;
import com.hexlindia.drool.user.dto.ContributionSummaryDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.mapper.UserProfileMapper;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.video.business.api.Video;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserProfileImpl implements UserProfile {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileMapper userProfileMapper;

    private final Video video;

    @Override
    public UserProfileDto create(UserProfileDto userProfileDto) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileDto));
        return this.userProfileMapper.toDto(userProfileEntity);
    }

    @Override
    public UserProfileDto findById(String id) {
        Optional<UserProfileEntity> userProfileEntityOptional = this.userProfileRepository.findById(Long.valueOf(id));
        if (userProfileEntityOptional.isPresent()) {
            log.debug("User with id {} found", id);
            return userProfileMapper.toDto(userProfileEntityOptional.get());
        }
        throw new UserProfileNotFoundException("User profile with id " + id + " not found");
    }

    @Override
    public UserProfileDto update(UserProfileDto userProfileDto) {
        UserProfileEntity userProfileEntity = this.userProfileRepository.save(userProfileMapper.toEntity(userProfileDto));
        log.debug("User profile after update {}", userProfileEntity);
        return this.userProfileMapper.toDto(userProfileEntity);
    }

    @Override
    public ContributionSummaryDto getContributionSummary(String userId) {
        return new ContributionSummaryDto(video.getLatestThreeVideoThumbnails(userId));
    }
}
