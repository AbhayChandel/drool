package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.UserDetailsWithId;
import com.hexlindia.drool.user.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import com.hexlindia.drool.user.services.AuthenticatedUserDetails;
import com.hexlindia.drool.user.services.JwtResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUserAuthenticationImpl implements JwtUserAuthentication {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserProfile userProfile;

    @Autowired
    public JwtUserAuthenticationImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserProfile userProfile) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userProfile = userProfile;
    }


    @Override
    public JwtResponse authenticate(String username, String password) {
        Authentication authentication = usernamePasswordAuthenticate(username, password);
        log.info("User authenticated successfully");
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetailsWithId) {
            UserProfileDto userProfileDto = userProfile.findById(((UserDetailsWithId) principal).getUserId());
            return new JwtResponse(jwtUtil.generateToken(username), new AuthenticatedUserDetails(userProfileDto.getId(), userProfileDto.getUsername()));
        }
        throw new UserAccountNotFoundException("");
    }

    private Authentication usernamePasswordAuthenticate(String username, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
