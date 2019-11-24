package com.hexlindia.drool.usermanagement.security.business;

import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import com.hexlindia.drool.usermanagement.security.data.repository.UserAuthenticationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtUserDetailsServiceTest {

    @Autowired
    JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    UserAuthenticationRepository userAuthenticationRepository;

    @Test
    void testLoadUserByUsername_ValidCredentials() {
        UserAuthenticationEntity userAuthenticationEntity = new UserAuthenticationEntity();
        userAuthenticationEntity.setEmail("sonam99@gmail.com");
        userAuthenticationEntity.setPassword("soni");
        when(userAuthenticationRepository.findByEmail("sonam99@gmail.com")).thenReturn(Optional.of(userAuthenticationEntity));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        assertEquals("soni", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_InvalidCredentials() {
        when(userAuthenticationRepository.findByEmail("sonam99@gmail.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        });
    }
}