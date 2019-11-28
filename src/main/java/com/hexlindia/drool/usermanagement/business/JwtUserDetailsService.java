package com.hexlindia.drool.usermanagement.business;

import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import com.hexlindia.drool.usermanagement.data.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    @Autowired
    JwtUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserAccountEntity> userAuthenticationEntity = userAccountRepository.findByEmail(email);
        if (userAuthenticationEntity.isPresent()) {
            return new User(userAuthenticationEntity.get().getEmail(), userAuthenticationEntity.get().getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}
