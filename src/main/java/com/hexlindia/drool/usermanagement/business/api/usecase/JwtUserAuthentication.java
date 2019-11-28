package com.hexlindia.drool.usermanagement.business.api.usecase;

public interface JwtUserAuthentication {

    String authenticate(String username, String password);
}
