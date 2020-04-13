package com.hexlindia.drool.user.business;

import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import org.bson.types.ObjectId;
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
        UserAccountDoc userAccountDoc = new UserAccountDoc();
        userAccountDoc.setId(ObjectId.get());
        userAccountDoc.setEmailId("sonam99@gmail.com");
        userAccountDoc.setPassword("soni");
        when(userAccountRepository.findByEmail("sonam99@gmail.com")).thenReturn(Optional.of(userAccountDoc));

        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        assertEquals("soni", userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsername_InvalidCredentials() {
        when(userAccountRepository.findByEmail("sonam99@gmail.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            jwtUserDetailsService.loadUserByUsername("sonam99@gmail.com");
        });
    }
}