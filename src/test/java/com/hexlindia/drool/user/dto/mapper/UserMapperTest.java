package com.hexlindia.drool.user.dto.mapper;

import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserMapperTest {

    @MockBean
    UserAccountRepository userAccountRepositoryMock;

    UserMapper userMapperSpy;

    @BeforeEach
    void setUp() {
        userMapperSpy = Mockito.spy(new UserMapper(userAccountRepositoryMock));
    }

    @Test
    void stringToEntity_UserIdNullOrEmpty() {
        assertThrows(RequestParameterNotValidException.class, () -> {
            userMapperSpy.stringToEntity(null);
        });

        assertThrows(RequestParameterNotValidException.class, () -> {
            userMapperSpy.stringToEntity("");
        });
    }

    @Test
    void stringToEntity() {
        when(this.userAccountRepositoryMock.findById(any())).thenReturn(Optional.of(new UserAccountEntity()));
        userMapperSpy.stringToEntity("1");
        verify(this.userMapperSpy, times(1)).getUserAccountEntity(any());
    }

    @Test
    void getUserAccountEntity_NotFound() {
        when(this.userAccountRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserAccountNotFoundException.class, () -> {
            userMapperSpy.getUserAccountEntity("1");
        });
    }

    @Test
    void getUserAccountEntity_Found() {
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(44L);
        when(this.userAccountRepositoryMock.findById(any())).thenReturn(Optional.of(userAccountEntityMock));
        assertEquals(44L, userMapperSpy.getUserAccountEntity("1").getId());
    }
}