package com.hexlindia.drool.user.business.api.usecase;

public interface JwtUserAuthentication {

    String authenticate(String username, String password);
}
