package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.UserDetailsWithId;
import com.hexlindia.drool.user.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import com.hexlindia.drool.user.services.AuthenticatedUserDetails;
import com.hexlindia.drool.user.services.JwtResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUserAuthenticationImpl implements JwtUserAuthentication {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public JwtResponse authenticate(String emailId, String password) {
        Authentication authentication = usernamePasswordAuthenticate(emailId, password);
        log.info("User authenticated successfully");
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsWithId) {
            return new JwtResponse(jwtUtil.generateToken(emailId), new AuthenticatedUserDetails(((UserDetailsWithId) principal).getUserId(), ((UserDetailsWithId) principal).getUsername()));
        }
        throw new UserAccountNotFoundException("");
    }

    private Authentication usernamePasswordAuthenticate(String emailId, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));
    }
}
