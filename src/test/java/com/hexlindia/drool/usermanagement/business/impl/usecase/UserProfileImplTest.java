package com.hexlindia.drool.usermanagement.business.impl.usecase;


import com.hexlindia.drool.usermanagement.business.api.to.UserProfileTo;
import com.hexlindia.drool.usermanagement.business.api.to.mapper.UserProfileMapper;
import com.hexlindia.drool.usermanagement.data.entity.UserProfileEntity;
import com.hexlindia.drool.usermanagement.data.repository.UserProfileRepository;
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

    @BeforeEach
    void setUp() {
        this.userProfileImpl = Mockito.spy(new UserProfileImpl(this.userProfileRepository, this.userProfileMapperMocked));
    }

    @Test
    void create_PassingObjectToRepositoryLayer() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity("kriti99", 9876543210L, "Indore", 'F');
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(userProfileEntityMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);
        this.userProfileImpl.create(new UserProfileTo(0L, "", 0L, "", 'M'));
        ArgumentCaptor<UserProfileEntity> userProfileEntityArgumentCaptor = ArgumentCaptor.forClass(UserProfileEntity.class);
        verify(this.userProfileRepository, times(1)).save(userProfileEntityArgumentCaptor.capture());
        assertEquals("kriti99", userProfileEntityArgumentCaptor.getValue().getUsername());
        assertEquals(9876543210L, userProfileEntityArgumentCaptor.getValue().getMobile());
        assertEquals("Indore", userProfileEntityArgumentCaptor.getValue().getCity());
        assertEquals('F', userProfileEntityArgumentCaptor.getValue().getGender());
    }

    @Test
    void create_ObjectReturnedFromRepositoryLayerIsReceived() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity("kriti99", 0, "", '\n');
        userProfileEntityMocked.setId(5L);
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(null);
        when(this.userProfileRepository.save(any())).thenReturn(userProfileEntityMocked);
        UserProfileTo userProfileToMocked = new UserProfileTo();
        userProfileToMocked.setId(userProfileEntityMocked.getId());
        userProfileToMocked.setUsername(userProfileEntityMocked.getUsername());
        when(this.userProfileMapperMocked.toTransferObject(userProfileEntityMocked)).thenReturn(userProfileToMocked);
        UserProfileTo userProfileTo = this.userProfileImpl.create(new UserProfileTo(0L, "", 0L, "", 'M'));
        assertEquals(5L, userProfileTo.getId());
        assertEquals("kriti99", userProfileTo.getUsername());
    }

    @Test
    void create_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userProfileImpl.create(null));
    }

    @Test
    void findByUsername_testPassingEntityToRepository() {
        when(this.userProfileRepository.findByUsername("priya21")).thenReturn(Optional.of(new UserProfileEntity()));
        userProfileImpl.findByUsername("priya21");
        ArgumentCaptor<String> usernameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(userProfileRepository, times(1)).findByUsername(usernameArgumentCaptor.capture());
        assertEquals("priya21", usernameArgumentCaptor.getValue());
    }

    @Test
    void findByUsername_testObjectPassedByRepositoryReceived() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity();
        UserProfileTo userProfileToMocked = new UserProfileTo();
        userProfileToMocked.setId(2L);
        userProfileToMocked.setUsername("priya21");
        userProfileToMocked.setMobile(8765432109L);
        userProfileToMocked.setCity("Pune");
        userProfileToMocked.setGender('F');
        Optional<UserProfileEntity> userProfileEntityOptional = Optional.of(userProfileEntityMocked);
        when(this.userProfileRepository.findByUsername(anyString())).thenReturn(userProfileEntityOptional);
        when(this.userProfileMapperMocked.toTransferObject(userProfileEntityMocked)).thenReturn(userProfileToMocked);
        UserProfileTo userProfileToReturned = userProfileImpl.findByUsername("priya21");
        assertEquals(2L, userProfileToReturned.getId());
        assertEquals("priya21", userProfileToReturned.getUsername());
        assertEquals(8765432109L, userProfileToReturned.getMobile());
        assertEquals("Pune", userProfileToReturned.getCity());
        assertEquals('F', userProfileToReturned.getGender());
    }

    @Test
    void findByUsername_testNoProfileFound() {
        when(this.userProfileRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        UserProfileTo userProfileToReturned = userProfileImpl.findByUsername("gg21");
        assertEquals(0L, userProfileToReturned.getId());
    }

    @Test
    void update_PassingObjectToRepositoryLayer() {
        UserProfileEntity userProfileEntityMocked = new UserProfileEntity("kriti99", 9876543210L, "Indore", 'F');
        userProfileEntityMocked.setId(2L);
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(userProfileEntityMocked);
        when(this.userProfileRepository.save(any())).thenReturn(null);
        this.userProfileImpl.update(new UserProfileTo(0L, "", 0L, "", 'M'));
        ArgumentCaptor<UserProfileEntity> userProfileEntityArgumentCaptor = ArgumentCaptor.forClass(UserProfileEntity.class);
        verify(this.userProfileRepository, times(1)).save(userProfileEntityArgumentCaptor.capture());
        assertEquals(2L, userProfileEntityArgumentCaptor.getValue().getId());
        assertEquals("kriti99", userProfileEntityArgumentCaptor.getValue().getUsername());
        assertEquals(9876543210L, userProfileEntityArgumentCaptor.getValue().getMobile());
        assertEquals("Indore", userProfileEntityArgumentCaptor.getValue().getCity());
        assertEquals('F', userProfileEntityArgumentCaptor.getValue().getGender());
    }

    @Test
    void update_testRepositoryLayerThrowsError() {
        doThrow(new DataIntegrityViolationException("")).when(this.userProfileRepository).save(any());
        when(this.userProfileMapperMocked.toEntity(any())).thenReturn(null);
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> userProfileImpl.update(null));
    }

}