package com.hexlindia.drool.user.business.api.usecase;

import com.hexlindia.drool.user.services.JwtResponse;

public interface JwtUserAuthentication {

    JwtResponse authenticate(String username, String password);
}
