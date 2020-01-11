package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.to.UserAccountTo;
import com.hexlindia.drool.user.business.api.to.UserProfileTo;
import com.hexlindia.drool.user.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.user.business.api.to.mapper.RegistrationToUserProfileMapper;
import com.hexlindia.drool.user.business.api.to.mapper.UserAccountMapper;
import com.hexlindia.drool.user.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.UserAccountRepository;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
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
    private JwtUtil jwtUtil;

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
        this.userAccountImpl = Mockito.spy(new UserAccountImpl(userAccountRepository, userRegistrationDetailsMapper, jwtUtil, passwordEncoder, userProfile, registrationToUserProfileMapper, userAccountMapper));
    }

    @Test
    void register_testPassingEntityToUserAccountRepository() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntity);
        userAuthenticationEntity.setId(2L);
        when(this.userAccountRepository.saveAndFlush(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);
        when(this.registrationToUserProfileMapper.toUserProfileTo(any())).thenReturn(new UserProfileTo());
        UserProfileTo userProfileToMocked = new UserProfileTo(1L, "pirya", 0, null, 'M');
        when(this.userProfile.create(any())).thenReturn(userProfileToMocked);
        userAccountImpl.register(new UserRegistrationDetailsTo("", "", ""));
        ArgumentCaptor<UserAccountEntity> userAuthenticationEntityArgumentCaptor = ArgumentCaptor.forClass(UserAccountEntity.class);
        verify(userAccountRepository, times(1)).saveAndFlush(userAuthenticationEntityArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", userAuthenticationEntityArgumentCaptor.getValue().getEmail());
    }

    @Test
    void register_testPassingArgumentToUserProfile() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntity);
        userAuthenticationEntity.setId(2L);
        when(this.userAccountRepository.saveAndFlush(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);
        UserProfileTo userProfileToMocked = new UserProfileTo(1L, "pirya", 0, null, 'M');
        when(this.registrationToUserProfileMapper.toUserProfileTo(any())).thenReturn(userProfileToMocked);
        when(this.userProfile.create(any())).thenReturn(userProfileToMocked);
        userAccountImpl.register(new UserRegistrationDetailsTo("", "", ""));
        ArgumentCaptor<UserProfileTo> userProfileToArgumentCaptor = ArgumentCaptor.forClass(UserProfileTo.class);
        verify(userProfile, times(1)).create(userProfileToArgumentCaptor.capture());
        assertEquals(2L, userProfileToArgumentCaptor.getValue().getId());
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
        Assertions.assertThrows(UserAccountNotFoundException.class, () -> userAccountImpl.findByEmail("kriti99@gmail.com"));
    }
}