package com.hexlindia.drool.usermanagement.security.business.api.usecase;

public interface JwtUserAuthentication {

    String authenticate(String username, String password);
}
