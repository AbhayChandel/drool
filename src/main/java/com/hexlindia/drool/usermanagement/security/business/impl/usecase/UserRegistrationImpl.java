package com.hexlindia.drool.usermanagement.security.business.impl.usecase;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.UserRegistration;
import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import com.hexlindia.drool.usermanagement.security.data.repository.UserAuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class UserRegistrationImpl implements UserRegistration {

    private final UserAuthenticationRepository userAuthenticationRepository;

    private final UserRegistrationDetailsMapper userRegistrationDetailsMapper;

    private final JwtUserAuthentication jwtUserAuthentication;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    protected UserRegistrationImpl(UserAuthenticationRepository userAuthenticationRepository, UserRegistrationDetailsMapper userRegistrationDetailsMapper, JwtUserAuthentication jwtUserAuthentication, PasswordEncoder passwordEncoder) {
        this.userAuthenticationRepository = userAuthenticationRepository;
        this.userRegistrationDetailsMapper = userRegistrationDetailsMapper;
        this.jwtUserAuthentication = jwtUserAuthentication;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String register(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        userAuthenticationRepository.saveAndFlush(getUserAuthenticationEntity(userRegistrationDetailsTo));
        return getJwtToken(userRegistrationDetailsTo);
    }

    UserAuthenticationEntity getUserAuthenticationEntity(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        UserAuthenticationEntity userAuthenticationEntity = this.userRegistrationDetailsMapper.toEntity(userRegistrationDetailsTo);
        setEncodedPasswordInEntity(userAuthenticationEntity);
        return userAuthenticationEntity;
    }

    void setEncodedPasswordInEntity(UserAuthenticationEntity userAuthenticationEntity) {
        userAuthenticationEntity.setPassword(getEncodedPassword(userAuthenticationEntity.getPassword()));
    }

    String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    String getJwtToken(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        return jwtUserAuthentication.authenticate(userRegistrationDetailsTo.getEmail(), userRegistrationDetailsTo.getPassword());
    }
}
