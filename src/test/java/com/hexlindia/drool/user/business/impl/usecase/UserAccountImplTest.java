package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.JwtUtil;
import com.hexlindia.drool.user.business.api.usecase.UserProfile;
import com.hexlindia.drool.user.data.doc.UserAccountDoc;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.dto.UserAccountDto;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.UserRegistrationDto;
import com.hexlindia.drool.user.dto.mapper.UserAccountMapper;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    private JwtUtil jwtUtil;

    @Mock
    private UserProfile userProfileMocked;

    @Mock
    private UserAccountMapper userAccountMapperMocked;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserAccountImpl userAccountImpl;

    @BeforeEach
    void setUp() {
        this.userAccountImpl = Mockito.spy(new UserAccountImpl(userAccountRepositoryMocked, jwtUtil, userProfileMocked, passwordEncoder, userAccountMapperMocked));
    }

    @Test
    void register_testPassingDocToUserAccountRepository() {

        UserAccountDoc userAccountDoc = new UserAccountDoc();
        userAccountDoc.setEmailId("kriti99@gmail.com");
        userAccountDoc.setPassword(passwordEncoder.encode("kriti"));
        userAccountDoc.setId(ObjectId.get());
        when(this.userAccountMapperMocked.toDoc(any())).thenReturn(userAccountDoc);
        when(this.userAccountRepositoryMocked.save(any())).thenReturn(userAccountDoc);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);

        when(this.userProfileMocked.create(any())).thenReturn(new UserProfileDto());

        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUserAccountDto(new UserAccountDto());
        userRegistrationDto.setUserProfileDto(new UserProfileDto());
        userAccountImpl.register(userRegistrationDto);
        ArgumentCaptor<UserAccountDoc> userAccountDocArgumentCaptor = ArgumentCaptor.forClass(UserAccountDoc.class);
        verify(userAccountRepositoryMocked, times(1)).save(userAccountDocArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", userAccountDocArgumentCaptor.getValue().getEmailId());
    }

    @Test
    void register_testPassingArgumentToUserProfile() {
        UserAccountDoc userAccountDoc = new UserAccountDoc();
        userAccountDoc.setEmailId("kriti99@gmail.com");
        userAccountDoc.setPassword(passwordEncoder.encode("kriti"));
        ObjectId accountId = new ObjectId();

        userAccountDoc.setId(accountId);
        when(this.userAccountMapperMocked.toDoc(any())).thenReturn(userAccountDoc);
        when(this.userAccountRepositoryMocked.save(any())).thenReturn(userAccountDoc);
        when(this.jwtUtil.generateToken(anyString())).thenReturn(null);

        UserProfileDto userProfileDtoMocked = new UserProfileDto();

        userProfileDtoMocked.setCity("Chandigarh");
        userProfileDtoMocked.setGender("M");
        userProfileDtoMocked.setMobile("9876543210");
        userProfileDtoMocked.setName("Ajay Singh");
        userProfileDtoMocked.setUsername("Ajayboss");
        when(this.userProfileMocked.create(any())).thenReturn(userProfileDtoMocked);
        UserRegistrationDto userRegistrationDtoMocked = new UserRegistrationDto();
        userRegistrationDtoMocked.setUserProfileDto(userProfileDtoMocked);
        userRegistrationDtoMocked.setUserAccountDto(new UserAccountDto());
        userAccountImpl.register(userRegistrationDtoMocked);
        ArgumentCaptor<UserProfileDto> userProfileDtoArgumentCaptor = ArgumentCaptor.forClass(UserProfileDto.class);
        verify(userProfileMocked, times(1)).create(userProfileDtoArgumentCaptor.capture());
        assertEquals(accountId.toHexString(), userProfileDtoArgumentCaptor.getValue().getId());
        assertEquals("Chandigarh", userProfileDtoArgumentCaptor.getValue().getCity());
        assertEquals("M", userProfileDtoArgumentCaptor.getValue().getGender());
        assertEquals("9876543210", userProfileDtoArgumentCaptor.getValue().getMobile());
        assertEquals("Ajay Singh", userProfileDtoArgumentCaptor.getValue().getName());
        assertEquals("Ajayboss", userProfileDtoArgumentCaptor.getValue().getUsername());
    }

    @Test
    void getEncodedPassword_passwordIsEncodedCorrectly() {
        String passwordToEncode = "priyaa";
        String passwordEncoded = this.userAccountImpl.getEncodedPassword(passwordToEncode);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        assertTrue(bCryptPasswordEncoder.matches(passwordToEncode, passwordEncoded));
    }

    @Test
    void findByEmail_testPassingDocToRepository() {
        when(this.userAccountRepositoryMocked.findByEmail("kriti99@gmail.com")).thenReturn(Optional.of(new UserAccountDoc()));
        userAccountImpl.findByEmail("kriti99@gmail.com");
        ArgumentCaptor<String> findByEmailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userAccountRepositoryMocked, times(1)).findByEmail(findByEmailArgumentCaptor.capture());
        assertEquals("kriti99@gmail.com", findByEmailArgumentCaptor.getValue());
    }

    @Test
    void findByEmail_testNoAccountFound() {
        when(this.userAccountRepositoryMocked.findByEmail(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserAccountNotFoundException.class, () -> userAccountImpl.findByEmail("kriti99@gmail.com"));
    }
}