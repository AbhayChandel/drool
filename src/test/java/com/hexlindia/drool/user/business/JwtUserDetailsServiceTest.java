package com.hexlindia.drool.user.business;

import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
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
    UserAccountRepository userAccountRepository;

    @Test
    void testLoadUserByUsername_ValidCredentials() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("sonam99@gmail.com");
        userAuthenticationEntity.setPassword("soni");
        when(userAccountRepository.findUser("sonam99@gmail.com")).thenReturn(Optional.of(userAuthenticationEntity));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        assertEquals("soni", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_InvalidCredentials() {
        when(userAccountRepository.findUser("sonam99@gmail.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        });
    }
}