package com.hexlindia.drool.usermanagement.security.business;

import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import com.hexlindia.drool.usermanagement.security.data.repository.UserAuthenticationRepository;
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

    private final UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    JwtUserDetailsService(UserAuthenticationRepository userAuthenticationRepository) {
        this.userAuthenticationRepository = userAuthenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<UserAuthenticationEntity> userAuthenticationEntity = userAuthenticationRepository.findByEmail(email);
        if (userAuthenticationEntity.isPresent()) {
            return new User(userAuthenticationEntity.get().getEmail(), userAuthenticationEntity.get().getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
    }
}
