package com.hexlindia.drool.usermanagement.security.business.impl.usecase;

import com.hexlindia.drool.usermanagement.security.business.api.to.UserRegistrationDetailsTo;
import com.hexlindia.drool.usermanagement.security.business.api.to.mapper.UserRegistrationDetailsMapper;
import com.hexlindia.drool.usermanagement.security.business.api.usecase.JwtUserAuthentication;
import com.hexlindia.drool.usermanagement.security.data.entity.UserAuthenticationEntity;
import com.hexlindia.drool.usermanagement.security.data.repository.UserAuthenticationRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserRegistrationImplTest {

    @Mock
    private UserAuthenticationRepository userAuthenticationRepository;

    @Mock
    private UserRegistrationDetailsMapper userRegistrationDetailsMapper;

    @Mock
    private JwtUserAuthentication jwtUserAuthentication;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserRegistrationImpl userRegistrationImpl;

    @BeforeEach
    void setUp() {
        this.userRegistrationImpl = Mockito.spy(new com.hexlindia.drool.usermanagement.security.business.impl.usecase.UserRegistrationImpl(userAuthenticationRepository, userRegistrationDetailsMapper, jwtUserAuthentication, passwordEncoder));
    }

    @Test
    void register_testPassingEntityToRepository() {
        UserAuthenticationEntity userAuthenticationEntity = new UserAuthenticationEntity();
        userAuthenticationEntity.setEmail("kriti99@gmail.com");
        userAuthenticationEntity.setPassword(passwordEncoder.encode("kriti"));
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntity);
        when(this.jwtUserAuthentication.authenticate(any(), any())).thenReturn("");
        userRegistrationImpl.register(new UserRegistrationDetailsTo("", "", ""));
        ArgumentCaptor<UserAuthenticationEntity> userAuthenticationEntityArgumentCaptor = ArgumentCaptor.forClass(UserAuthenticationEntity.class);
        verify(userAuthenticationRepository, times(1)).saveAndFlush(userAuthenticationEntityArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", userAuthenticationEntityArgumentCaptor.getValue().getEmail());
    }

    @Test
    void register_testRepositoryThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userRegistrationImpl).register(any());
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userRegistrationImpl.register(null));
    }

    @Test
    void getUserAuthenticationEntity_testReturningObject() {
        UserAuthenticationEntity userAuthenticationEntityMocked = new UserAuthenticationEntity();
        userAuthenticationEntityMocked.setEmail("payal50@gmail.com");
        userAuthenticationEntityMocked.setPassword("password");
        when(this.userRegistrationDetailsMapper.toEntity(any())).thenReturn(userAuthenticationEntityMocked);
        Mockito.doNothing().when(this.userRegistrationImpl).setEncodedPasswordInEntity(any());
        UserAuthenticationEntity userAuthenticationEntityReturned = userRegistrationImpl.getUserAuthenticationEntity(new UserRegistrationDetailsTo("", "", ""));
        assertEquals("payal50@gmail.com", userAuthenticationEntityReturned.getEmail());
        assertEquals("password", userAuthenticationEntityReturned.getPassword());
    }

    @Test
    void getEncodedPassword_passwordIsEncodedCorrectly() {
        String passwordToEncode = "priyaa";
        String passwordEncoded = this.userRegistrationImpl.getEncodedPassword(passwordToEncode);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        assertTrue(bCryptPasswordEncoder.matches(passwordToEncode, passwordEncoded));
    }

    @Test
    void getJwtToken_passwordIsEncodedCorrectly() {
        when(this.jwtUserAuthentication.authenticate(anyString(), anyString())).thenReturn("token");
        assertEquals("token", this.userRegistrationImpl.getJwtToken(new UserRegistrationDetailsTo("", "", "")));
    }
}