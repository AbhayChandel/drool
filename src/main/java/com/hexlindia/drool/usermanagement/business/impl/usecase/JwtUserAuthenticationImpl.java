package com.hexlindia.drool.usermanagement.business.impl.usecase;

import com.hexlindia.drool.usermanagement.business.JwtUserDetailsService;
import com.hexlindia.drool.usermanagement.business.JwtUtil;
import com.hexlindia.drool.usermanagement.business.api.usecase.JwtUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUserAuthenticationImpl implements JwtUserAuthentication {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtUserAuthenticationImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtUserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }


    @Override
    public String authenticate(String username, String password) {
        usernamePasswordAuthenticate(username, password);

        final UserDetails userDetails = jwtUserDetailsService
                .loadUserByUsername(username);

        log.debug("UserDetails: {}", userDetails.toString());
        return jwtUtil.generateToken(userDetails);
    }

    private void usernamePasswordAuthenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        log.info("User authenticated successfully");
    }
}
