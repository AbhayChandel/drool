package com.hexlindia.drool.usermanagement.security.jwt.business.impl;

import com.hexlindia.drool.usermanagement.security.jwt.business.JwtUserDetailsService;
import com.hexlindia.drool.usermanagement.security.jwt.business.JwtUtil;
import com.hexlindia.drool.usermanagement.security.jwt.business.api.JwtUserAuthentication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtUserAuthenticationImplTest {


    private JwtUserAuthentication jwtUserAuthentication;

    @MockBean
    JwtUserDetailsService jwtUserDetailsServiceMocked;

    @MockBean
    JwtUtil jwtUtilMocked;

    @MockBean
    AuthenticationManager authenticationManagerMocked;

    @BeforeEach
    void setUp() {
        this.jwtUserAuthentication = Mockito.spy(new JwtUserAuthenticationImpl(authenticationManagerMocked, jwtUtilMocked, jwtUserDetailsServiceMocked));
    }


    @Test
    void authenticate() {
        UserDetails userDetails = new User("priya.singh@gmail.com", "$2y$12$nfSQJzgpJ3gTu.CczB6BiuceNqA6niFi7EX03p4Ep3205kL5I2pDy", new ArrayList<>());
        when(jwtUserDetailsServiceMocked.loadUserByUsername("priya.singh@gmail.com")).thenReturn(userDetails);
        when(jwtUtilMocked.generateToken(userDetails)).thenReturn("token");
        String token = jwtUserAuthentication.authenticate("priya.singh@gmail.com", "priya");
        assertNotNull(token);
    }

    @Test
    void testUsernamePasswordAuthenticate_InvalidCredentials() {
        doThrow(new BadCredentialsException("Bad Credentials")).when(this.authenticationManagerMocked).authenticate(new UsernamePasswordAuthenticationToken("Shelly@gmail.com", "shelly"));

        Assertions.assertThrows(AuthenticationException.class, () -> jwtUserAuthentication.authenticate("Shelly@gmail.com", "shelly"));
       /*Assertions.assertThrows(AuthenticationException.class, () -> {
           jwtUserAuthentication.authenticate("Shelly@gmail.com", "shelly");
       });*/
    }

}