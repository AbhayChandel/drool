package com.hexlindia.drool.usermanagement.security.jwt.business.api;

import org.springframework.stereotype.Component;

@Component
public interface JwtUserAuthentication {

    String authenticate(String username, String password);
}
