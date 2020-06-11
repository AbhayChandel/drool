package com.hexlindia.drool.user.services.impl.rest;

import com.hexlindia.drool.user.business.api.usecase.UserAccount;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.dto.validation.NewAccount;
import com.hexlindia.drool.user.services.JwtResponse;
import com.hexlindia.drool.user.services.api.rest.UserAccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserAccountRestServiceImpl implements UserAccountRestService {

    private final UserAccount userAccount;

    @Autowired
    public UserAccountRestServiceImpl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Override
    public ResponseEntity<JwtResponse> register(@Validated(NewAccount.class) UserRegistrationDto userRegistrationDto) {
        return ResponseEntity.ok(userAccount.register(userRegistrationDto));
    }

    @Override
    public ResponseEntity<UserAccountDto> findByEmail(String userIdentifier) {
        UserAccountDto userAccountDto = this.userAccount.findUser(userIdentifier);
        return ResponseEntity.ok(userAccountDto);
    }
}
