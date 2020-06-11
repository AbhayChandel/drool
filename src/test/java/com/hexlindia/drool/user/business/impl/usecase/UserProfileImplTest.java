package com.hexlindia.drool.user.business.impl.usecase;


import com.hexlindia.drool.user.data.entity.UserProfileEntity;
import com.hexlindia.drool.user.data.repository.api.UserProfileRepository;
import com.hexlindia.drool.user.dto.UserProfileDto;
import com.hexlindia.drool.user.dto.mapper.UserProfileMapper;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.video.business.api.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserProfileImplTest {

    private UserProfileImpl userProfileImpl;

    @Mock
    private UserProfileMapper userProfileMapperMocked;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private Video videoMock;

    @BeforeEach
    void setUp() {
        this.userProfileImpl = Mockito.spy(new UserProfileImpl(this.userProfileRepository, this.userProfileMapperMocked, this.videoMock));
    }

    @Test
    void create_PassingObjectToRepositoryLayer() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity();
        userProfileEntityMocked.setCity("Indore");
        userProfileEntityMocked.setGender("F");
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(userProfileEntityMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);

        this.userProfileImpl.create(null);

        ArgumentCaptor<UserProfileEntity> userProfileEntityArgumentCaptor = ArgumentCaptor.forClass(UserProfileEntity.class);
        verify(this.userProfileRepository, times(1)).save(userProfileEntityArgumentCaptor.capture());
        assertEquals("Indore", userProfileEntityArgumentCaptor.getValue().getCity());
        assertEquals("F", userProfileEntityArgumentCaptor.getValue().getGender());
    }

    @Test
    void create_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userProfileImpl.create(null));
    }

    @Test
    void findById_testPassingArgumentsToRepository() {
        when(this.userProfileRepository.findById(1L)).thenReturn(Optional.of(new UserProfileEntity()));
        userProfileImpl.findById("1");
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(userProfileRepository, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(1L, idArgumentCaptor.getValue().longValue());
    }

    @Test
    void findById_noProfileFound() {
        when(this.userProfileRepository.findById(7L)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserProfileNotFoundException.class, () -> userProfileImpl.findById("7"));
    }

    @Test
    void update_PassingObjectToRepositoryLayer() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity();
        userProfileEntityMocked.setCity("Indore");
        userProfileEntityMocked.setGender("F");
        userProfileEntityMocked.setId(2L);
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(userProfileEntityMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);
        this.userProfileImpl.update(new UserProfileDto());
        ArgumentCaptor<UserProfileEntity> userProfileEntityArgumentCaptor = ArgumentCaptor.forClass(UserProfileEntity.class);
        verify(this.userProfileRepository, times(1)).save(userProfileEntityArgumentCaptor.capture());
        assertEquals(2L, userProfileEntityArgumentCaptor.getValue().getId());
        assertEquals("Indore", userProfileEntityArgumentCaptor.getValue().getCity());
        assertEquals("F", userProfileEntityArgumentCaptor.getValue().getGender());
    }

    @Test
    void update_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userProfileImpl.update(null));
    }

    @Test
    void contributionSummary_testPassingArgumentsToRepository() {
        when(this.videoMock.getLatestThreeVideoThumbnails("123")).thenReturn(null);
        userProfileImpl.getContributionSummary("123");
        ArgumentCaptor<String> userIdArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(videoMock, times(1)).getLatestThreeVideoThumbnails(userIdArgumentCaptor.capture());
        assertEquals("123", userIdArgumentCaptor.getValue());
    }

}