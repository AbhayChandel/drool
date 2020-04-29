package com.hexlindia.drool.user.business.impl.usecase;

import com.hexlindia.drool.user.business.api.usecase.UserActivity;
import com.hexlindia.drool.user.data.repository.api.UserActivityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserActivityImplTest {

    private UserActivity userActivitySpy;

    @Mock
    private UserActivityRepository userActivityRepositoryMock;

    @BeforeEach
    void setUp() {
        this.userActivitySpy = Mockito.spy(new UserActivityImpl(userActivityRepositoryMock));
    }

    }