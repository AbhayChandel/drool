package com.hexlindia.drool.usermanagement.business.impl.usecase;

import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.RegistrationToUserProfileMapper;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserAccountMapper;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.usermanagement.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserAccount;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserProfile;
import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import com.hexlindia.drool.usermanagement.data.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component
@Transactional
public class UserAccountImpl implements UserAccount {

    private final UserAccountRepository userAccountRepository;

    private final UserRegistrationDetailsMapper userRegistrationDetailsMapper;

    private final JwtUserAuthentication jwtUserAuthentication;

    private final UserProfile userProfile;

    private final RegistrationToUserProfileMapper registrationToUserProfileMapper;

    private final PasswordEncoder passwordEncoder;
    private final UserAccountMapper userAccountMapper;

    @Autowired
    protected UserAccountImpl(UserAccountRepository userAccountRepository, UserRegistrationDetailsMapper userRegistrationDetailsMapper, JwtUserAuthentication jwtUserAuthentication, PasswordEncoder passwordEncoder, UserProfile userProfile, RegistrationToUserProfileMapper registrationToUserProfileMapper, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userRegistrationDetailsMapper = userRegistrationDetailsMapper;
        this.jwtUserAuthentication = jwtUserAuthentication;
        this.passwordEncoder = passwordEncoder;
        this.userProfile = userProfile;
        this.registrationToUserProfileMapper = registrationToUserProfileMapper;
        this.userAccountMapper = userAccountMapper;
    }

    @Override
    public String register(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        userAccountRepository.saveAndFlush(getUserAuthenticationEntity(userRegistrationDetailsTo));
        userProfile.create(registrationToUserProfileMapper.toUserProfileTo(userRegistrationDetailsTo));
        return getJwtToken(userRegistrationDetailsTo);
    }

    @Override
    public UserAccountTo findByEmail(String email) {
        Optional<UserAccountEntity> userAccountEntityOptional = this.userAccountRepository.findByEmail(email);
        if (userAccountEntityOptional.isPresent()) {
            return userAccountMapper.toTransferObject(userAccountEntityOptional.get());
        }
        return new UserAccountTo();
    }

    UserAccountEntity getUserAuthenticationEntity(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        UserAccountEntity userAuthenticationEntity = this.userRegistrationDetailsMapper.toEntity(userRegistrationDetailsTo);
        setEncodedPasswordInEntity(userAuthenticationEntity);
        return userAuthenticationEntity;
    }

    void setEncodedPasswordInEntity(UserAccountEntity userAuthenticationEntity) {
        userAuthenticationEntity.setPassword(getEncodedPassword(userAuthenticationEntity.getPassword()));
    }

    String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    String getJwtToken(UserRegistrationDetailsTo userRegistrationDetailsTo) {
        return jwtUserAuthentication.authenticate(userRegistrationDetailsTo.getEmail(), userRegistrationDetailsTo.getPassword());
    }
}
