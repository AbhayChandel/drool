package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.common.dto.mapper.StringToEntityMapping;
import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {

    private final UserAccountRepository userAccountRepository;

    @StringToEntityMapping
    public UserAccountEntity stringToEntity(String userId) {
        if (userId == null || userId.isEmpty()) {
            log.error("While mapping userId to entity, userId is null or empty");
            throw new RequestParameterNotValidException("userId", "userId is null or empty");
        }
        return getUserAccountEntity(userId);
    }

    UserAccountEntity getUserAccountEntity(String userId) {
        Optional<UserAccountEntity> userAccountEntityOptional = userAccountRepository.findById(Long.valueOf(userId));
        if (userAccountEntityOptional.isPresent()) {
            return userAccountEntityOptional.get();
        }
        log.error("User account with id '" + userId + "' not found in data store");
        throw new UserAccountNotFoundException("User account with id '" + userId + "' not found in data store");
    }
}
