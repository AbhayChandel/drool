package com.hexlindia.drool.user.business;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
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
    public UserDetails loadUserByUsername(String email) {
        Optional<UserAccountDoc> userAccountDoc = userAccountRepository.findByEmail(email);
        if (userAccountDoc.isPresent()) {
            log.info("User with email {} found", email);
            return new UserDetailsWithId(userAccountDoc.get().getId().toHexString(), userAccountDoc.get().getEmailId(), userAccountDoc.get().getPassword(),
                    new ArrayList<>());
        } else {
            log.warn("User with email {} not found" + email);
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}
