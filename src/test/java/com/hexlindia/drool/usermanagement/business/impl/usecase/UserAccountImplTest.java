package com.hexlindia.drool.usermanagement.business.impl.usecase;

import com.hexlindia.drool.usermanagement.business.api.to.UserAccountTo;
import com.hexlindia.drool.usermanagement.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.RegistrationToUserProfileMapper;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserAccountMapper;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.usermanagement.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.business.api.usecase.UserProfile;
import com.hexlindia.drool.usermanagement.data.entity.UserAccountEntity;
import com.hexlindia.drool.usermanagement.data.repository.UserAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserAccountImplTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private UserRegistrationDetailsMapper userRegistrationDetailsMapper;

    @Mock
    private JwtUserAuthentication jwtUserAuthentication;

    @Mock
    private UserProfile userProfile;

    @Mock
    private RegistrationToUserProfileMapper registrationToUserProfileMapper;

    @Autowired
    private UserAccountMapper userAccountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccountImpl userAccountImpl;

    @BeforeEach
    void setUp() {
        this.userAccountImpl = Mockito.spy(new UserAccountImpl(userAccountRepository, userRegistrationDetailsMapper, jwtUserAuthentication, passwordEncoder, userProfile, registrationToUserProfileMapper, userAccountMapper));
    }

    @Test
    void register_testPassingEntityToRepository() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUserAuthentication.authenticate(any(), any())).thenReturn("");
        userAccountImpl.register(new UserRegistrationDetailsTo("", "", ""));
        ArgumentCaptor<UserAccountEntity> userAuthenticationEntityArgumentCaptor = ArgumentCaptor.forClass(UserAccountEntity.class);
        verify(userAccountRepository, times(1)).saveAndFlush(userAuthenticationEntityArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", userAuthenticationEntityArgumentCaptor.getValue().getEmail());
    }

    @Test
    void register_testRepositoryThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userAccountRepository).saveAndFlush(any());
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(null);
        Mockito.doNothing().when(this.userAccountImpl).setEncodedPasswordInEntity(any());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userAccountImpl.register(new UserRegistrationDetailsTo("", "", "")));
    }

    @Test
    void getUserAuthenticationEntity_testReturningObject() {
        UserAccountEntity userAuthenticationEntityMocked = new UserAccountEntity();
        userAuthenticationEntityMocked.setEmail("payal50@gmail.com");
        userAuthenticationEntityMocked.setPassword("password");
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntityMocked);
        Mockito.doNothing().when(this.userAccountImpl).setEncodedPasswordInEntity(any());
        UserAccountEntity userAuthenticationEntityReturned = userAccountImpl.getUserAuthenticationEntity(new UserRegistrationDetailsTo("", "", ""));
        assertEquals("payal50@gmail.com", userAuthenticationEntityReturned.getEmail());
        assertEquals("password", userAuthenticationEntityReturned.getPassword());
    }

    @Test
    void getEncodedPassword_passwordIsEncodedCorrectly() {
        String passwordToEncode = "priyaa";
        String passwordEncoded = this.userAccountImpl.getEncodedPassword(passwordToEncode);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        assertTrue(bCryptPasswordEncoder.matches(passwordToEncode, passwordEncoded));
    }

    @Test
    void getJwtToken_passwordIsEncodedCorrectly() {
        when(this.jwtUserAuthentication.authenticate(anyString(), anyString())).thenReturn("token");
        assertEquals("token", this.userAccountImpl.getJwtToken(new UserRegistrationDetailsTo("", "", "")));
    }

    @Test
    void findByEmail_testPassingEntityToRepository() {
        when(this.userAccountRepository.findByEmail("kriti99@gmail.com")).thenReturn(Optional.of(new UserAccountEntity()));
        userAccountImpl.findByEmail("kriti99@gmail.com");
        ArgumentCaptor<String> findByEmailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userAccountRepository, times(1)).findByEmail(findByEmailArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", findByEmailArgumentCaptor.getValue());
    }

    @Test
    void findByEmail_testObjectPassedByRepositoryReceived() {
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(3L);
        userAccountEntity.setEmail("sonam99@gmail.com");
        userAccountEntity.setPassword("$2y$12$nkEeE1P.hWfg1iqhp8JWOea9F7lEEzBi07ZdGs1ujrVJM5YVYnQqi");
        when(this.userAccountRepository.findByEmail(anyString())).thenReturn(Optional.of(userAccountEntity));
        UserAccountTo userAccountToReturned = userAccountImpl.findByEmail("kriti99@gmail.com");
        assertEquals("sonam99@gmail.com", userAccountToReturned.getEmail());
        assertEquals(3L, userAccountToReturned.getId());
    }

    @Test
    void findByEmail_testNoAccountFound() {
        when(this.userAccountRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        UserAccountTo userAccountToReturned = userAccountImpl.findByEmail("kriti99@gmail.com");
        assertEquals(0L, userAccountToReturned.getId());
    }
}