package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.services.JwtResponse;
import com.hexlindia.drool.user.services.api.rest.UserAccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserAccountRestServiceImpl implements UserAccountRestService {

    private final UserAccount userAccount;

    @Autowired
    public UserAccountRestServiceImpl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public ResponseEntity<JwtResponse> register(@Valid UserRegistrationDetailsTo userRegistrationDetailsTo) {
        return ResponseEntity.ok(new JwtResponse(userAccount.register(userRegistrationDetailsTo)));
    }

    @Override
    public ResponseEntity<UserAccountTo> findByEmail(String email) {
        return ResponseEntity.ok(this.userAccount.findByEmail(email));
    }
}
