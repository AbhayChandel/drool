package com.hexlindia.drool.user.business.impl.usecase;


import com.hexlindia.drool.user.data.doc.UserProfileDoc;
import com.hexlindia.drool.user.data.repository.api.UserProfileRepository;
import com.hexlindia.drool.user.dto.mapper.UserProfileMapper;
import com.hexlindia.drool.user.exception.UserProfileNotFoundException;
import com.hexlindia.drool.video.business.api.usecase.Video;
import org.bson.types.ObjectId;
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
        UserProfileDoc userProfileDocMocked = new UserProfileDoc();
        ObjectId accountId = new ObjectId();
        userProfileDocMocked.setId(accountId);
        userProfileDocMocked.setCity("Chandigarh");
        userProfileDocMocked.setGender("M");
        userProfileDocMocked.setMobile("9876543210");
        userProfileDocMocked.setName("Ajay Singh");
        userProfileDocMocked.setUsername("Ajayboss");
        when(this.userProfileMapperMocked.toDoc(any())).thenReturn(userProfileDocMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);
        this.userProfileImpl.create(null);
        ArgumentCaptor<UserProfileDoc> userProfileDocArgumentCaptor = ArgumentCaptor.forClass(UserProfileDoc.class);
        verify(this.userProfileRepository, times(1)).save(userProfileDocArgumentCaptor.capture());
        assertEquals(accountId, userProfileDocArgumentCaptor.getValue().getId());
        assertEquals("Chandigarh", userProfileDocArgumentCaptor.getValue().getCity());
        assertEquals("M", userProfileDocArgumentCaptor.getValue().getGender());
        assertEquals("9876543210", userProfileDocArgumentCaptor.getValue().getMobile());
        assertEquals("Ajay Singh", userProfileDocArgumentCaptor.getValue().getName());
        assertEquals("Ajayboss", userProfileDocArgumentCaptor.getValue().getUsername());
    }

    @Test
    void create_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toDoc(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userProfileImpl.create(null));
    }

    @Test
    void findById_testPassingArgumentsToRepository() {
        ObjectId accountIdMocked = new ObjectId();
        when(this.userProfileRepository.findById(accountIdMocked)).thenReturn(Optional.of(new UserProfileDoc()));
        userProfileImpl.findById(accountIdMocked.toHexString());
        ArgumentCaptor<ObjectId> idArgumentCaptor = ArgumentCaptor.forClass(ObjectId.class);
        verify(userProfileRepository, times(1)).findById(idArgumentCaptor.capture());
        assertEquals(accountIdMocked, idArgumentCaptor.getValue());
    }

    @Test
    void findById_noProfileFound() {
        when(this.userProfileRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserProfileNotFoundException.class, () -> userProfileImpl.findById(ObjectId.get().toHexString()));
    }

    @Test
    void findByUsername_testPassingArugumentsToRepository() {
        when(this.userProfileRepository.findByUsername("priya21")).thenReturn(Optional.of(new UserProfileDoc()));
        userProfileImpl.findByUsername("priya21");
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userProfileRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
        assertEquals("priya21", usernameArgumentCaptor.getValue());
    }


    @Test
    void findByUsername_testNoProfileFound() {
        when(this.userProfileRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(UserProfileNotFoundException.class, () -> userProfileImpl.findByUsername("gg21"));
    }

    @Test
    void update_PassingObjectToRepositoryLayer() {
        UserProfileDoc userProfileDocMocked = new UserProfileDoc();
        ObjectId accountId = new ObjectId();
        userProfileDocMocked.setId(accountId);
        userProfileDocMocked.setCity("Chandigarh");
        userProfileDocMocked.setGender("M");
        userProfileDocMocked.setMobile("9876543210");
        userProfileDocMocked.setName("Ajay Singh");
        userProfileDocMocked.setUsername("Ajayboss");
        when(this.userProfileMapperMocked.toDoc(any())).thenReturn(userProfileDocMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);
        this.userProfileImpl.update(null);
        ArgumentCaptor<UserProfileDoc> userProfileDocArgumentCaptor = ArgumentCaptor.forClass(UserProfileDoc.class);
        verify(this.userProfileRepository, times(1)).save(userProfileDocArgumentCaptor.capture());
        assertEquals(accountId, userProfileDocArgumentCaptor.getValue().getId());
        assertEquals("Chandigarh", userProfileDocArgumentCaptor.getValue().getCity());
        assertEquals("M", userProfileDocArgumentCaptor.getValue().getGender());
        assertEquals("9876543210", userProfileDocArgumentCaptor.getValue().getMobile());
        assertEquals("Ajay Singh", userProfileDocArgumentCaptor.getValue().getName());
        assertEquals("Ajayboss", userProfileDocArgumentCaptor.getValue().getUsername());
    }

    @Test
    void update_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toDoc(any())).thenReturn(null);
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