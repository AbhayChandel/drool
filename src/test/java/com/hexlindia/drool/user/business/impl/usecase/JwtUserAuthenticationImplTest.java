package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
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

import static org.mockito.Mockito.doThrow;

@SpringBootTest
class JwtUserAuthenticationImplTest {


    private JwtUserAuthentication jwtUserAuthentication;

    @MockBean
    UserProfile userProfileMocked;

    @MockBean
    JwtUtil jwtUtilMocked;

    @MockBean
    AuthenticationManager authenticationManagerMocked;

    @BeforeEach
    void setUp() {
        this.jwtUserAuthentication = Mockito.spy(new JwtUserAuthenticationImpl(authenticationManagerMocked, jwtUtilMocked, userProfileMocked));
    }

    @Test
    void testUsernamePasswordAuthenticate_InvalidCredentials() {
        doThrow(new BadCredentialsException("Bad Credentials")).when(this.authenticationManagerMocked).authenticate(new UsernamePasswordAuthenticationToken("Shelly@gmail.com", "shelly"));
        Assertions.assertThrows(AuthenticationException.class, () -> jwtUserAuthentication.authenticate("Shelly@gmail.com", "shelly"));
    }

}