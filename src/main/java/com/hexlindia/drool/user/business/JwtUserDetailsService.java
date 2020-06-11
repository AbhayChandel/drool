package com.hexlindia.drool.user.business;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Override
    public UserDetails loadUserByUsername(String userIdentifier) {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findUser(userIdentifier);
        if (userAuthenticationEntity.isPresent()) {
            log.info("User with email {} found", userIdentifier);
            UserAccountEntity userAccountEntity = userAuthenticationEntity.get();
            return new UserDetailsWithId(String.valueOf(userAccountEntity.getId()), userAccountEntity.getEmail(), userAccountEntity.getPassword(), userAccountEntity.getUsername(),
                    new ArrayList<>());
        } else {
            log.warn("User with email {} not found" + userIdentifier);
            throw new UsernameNotFoundException("User not found with username: " + userIdentifier);
        }
    }
}
