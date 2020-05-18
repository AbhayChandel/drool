package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.data.repository.api.UserVerificationRepository;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.dto.mapper.UserAccountMapper;
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
    private UserAccountRepository userAccountRepositoryMocked;

    @Mock
    private UserVerificationRepository userVerificationRepositoryMock;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserProfile userProfileMocked;

    @Mock
    private UserAccountMapper userAccountMapperMock;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccountImpl userAccountImpl;

    @BeforeEach
    void setUp() {
        this.userAccountImpl = Mockito.spy(new UserAccountImpl(userAccountRepositoryMocked, userVerificationRepositoryMock, jwtUtil, userProfileMocked, passwordEncoder, userAccountMapperMock));
    }

    @Test
    void register_testPassingEntityToUserAccountRepository() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        userAuthenticationEntity.setId(2L);
        when(this.userAccountMapperMock.toEntity(any())).thenReturn(userAuthenticationEntity);
        when(this.userAccountRepositoryMocked.saveAndFlush(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);
        when(this.userProfileMocked.create(any())).thenReturn(null);

        Mockito.doNothing().when(this.userAccountImpl).setEncodedPasswordInEntity(any());
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserProfileDto(new UserProfileDto());
        this.userAccountImpl.register(userRegistrationDto);
        ArgumentCaptor<UserAccountEntity> userAuthenticationEntityArgumentCaptor = ArgumentCaptor.forClass(UserAccountEntity.class);
        verify(userAccountRepositoryMocked, times(1)).saveAndFlush(userAuthenticationEntityArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", userAuthenticationEntityArgumentCaptor.getValue().getEmail());
    }

    @Test
    void register_testPassingArgumentToUserProfile() {
        UserAccountEntity userAuthenticationEntity = new UserAccountEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        userAuthenticationEntity.setId(2L);
        when(this.userAccountMapperMock.toEntity(any())).thenReturn(userAuthenticationEntity);

        when(this.userAccountRepositoryMocked.saveAndFlush(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);
        when(this.userProfileMocked.create(any())).thenReturn(null);

        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setName("Priya Singh");
        userProfileDto.setCity("Chandigarh");
        userProfileDto.setGender("F");

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserProfileDto(userProfileDto);
        this.userAccountImpl.register(userRegistrationDto);

        ArgumentCaptor<UserProfileDto> userProfileDtoArgumentCaptor = ArgumentCaptor.forClass(UserProfileDto.class);
        verify(userProfileMocked, times(1)).create(userProfileDtoArgumentCaptor.capture());
        assertEquals(userAuthenticationEntity.getId().toString(), userProfileDtoArgumentCaptor.getValue().getId());
        assertEquals("Priya Singh", userProfileDtoArgumentCaptor.getValue().getName());
        assertEquals("Chandigarh", userProfileDtoArgumentCaptor.getValue().getCity());
        assertEquals("F", userProfileDtoArgumentCaptor.getValue().getGender());
    }

    @Test
    void register_testRepositoryThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userAccountRepositoryMocked).saveAndFlush(any());
        when(this.userAccountMapperMock.toEntity(any())).thenReturn(null);
        Mockito.doNothing().when(this.userAccountImpl).setEncodedPasswordInEntity(any());
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userAccountImpl.register(new UserRegistrationDto()));
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
        when(this.userAccountRepositoryMocked.findUser("kriti99@gmail.com")).thenReturn(Optional.of(new UserAccountEntity()));
        userAccountImpl.findUser("kriti99@gmail.com");
        ArgumentCaptor<String> findByEmailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userAccountRepositoryMocked, times(1)).findUser(findByEmailArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", findByEmailArgumentCaptor.getValue());
    }

    @Test
    void findByEmail_testNoAccountFound() {
        when(this.userAccountRepositoryMocked.findUser(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserAccountNotFoundException.class, () -> userAccountImpl.findUser("kriti99@gmail.com"));
    }
}