package com.hexlindia.drool.collection.dto.mapper;

import com.hexlindia.drool.collection.data.entity.CollectionEntity;
import com.hexlindia.drool.collection.dto.CollectionPostDto;
import com.hexlindia.drool.common.data.constant.Visibility;
import com.hexlindia.drool.common.data.entity.VisibilityEntity;
import com.hexlindia.drool.common.data.repository.VisibilityRepository;
import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.common.error.exception.VisibilityNotFoundException;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import com.hexlindia.drool.user.exception.UserAccountNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CollectionPostMapperTest {

    @Autowired
    CollectionPostMapper collectionPostMapper;

    @MockBean
    VisibilityRepository visibilityRepositoryMock;

    @MockBean
    UserAccountRepository userAccountRepositoryMock;

    @Test
    void setVisibility_VisibilityNotFound() {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setVisibility(Visibility.PUBLIC);
        when(visibilityRepositoryMock.findByVisibility(any())).thenReturn(Optional.empty());
        assertThrows(VisibilityNotFoundException.class, () -> {
            collectionPostMapper.setVisibilityInEntity(collectionPostDto, null);
        });
    }

    @Test
    void setVisibility_VisibilityFound() {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setVisibility(Visibility.PUBLIC);
        when(visibilityRepositoryMock.findByVisibility(any())).thenReturn(Optional.of(new VisibilityEntity()));

        CollectionEntity collectionEntitySpy = spy(new CollectionEntity());
        collectionPostMapper.setVisibilityInEntity(collectionPostDto, collectionEntitySpy);

        verify(collectionEntitySpy, times(1)).setVisibility(any());
    }

    @Test
    void setOwner_OwnerIdIsNull() {
        assertThrows(RequestParameterNotValidException.class, () -> {
            collectionPostMapper.setOwner(new CollectionPostDto(), new CollectionEntity());
        });
    }

    @Test
    void setOwner_UserAccountNotFound() {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setOwnerId("1001");
        when(userAccountRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(UserAccountNotFoundException.class, () -> {
            collectionPostMapper.setOwner(collectionPostDto, new CollectionEntity());
        });
    }

    @Test
    void setOwner_UserAccountFound() {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setOwnerId("100");
        when(userAccountRepositoryMock.findById(any())).thenReturn(Optional.of(new UserAccountEntity()));

        CollectionEntity collectionEntitySpy = spy(new CollectionEntity());
        collectionPostMapper.setOwner(collectionPostDto, collectionEntitySpy);

        verify(collectionEntitySpy, times(1)).setOwner(any());
    }

    @Test
    void toEntity() {
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostDto.setCollectionId("10987");
        collectionPostDto.setName("Chic Styles");
        collectionPostDto.setAbout("This is about a Chic style");
        collectionPostDto.setVisibility(Visibility.PUBLIC);
        collectionPostDto.setOwnerId("1001");

        VisibilityEntity visibilityEntityMock = new VisibilityEntity();
        visibilityEntityMock.setId(4);
        when(visibilityRepositoryMock.findByVisibility(any())).thenReturn(Optional.of(visibilityEntityMock));
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(11L);
        when(userAccountRepositoryMock.findById(any())).thenReturn(Optional.of(userAccountEntityMock));

        CollectionEntity collectionEntity = collectionPostMapper.toEntity(collectionPostDto);

        assertEquals(10987, collectionEntity.getId());
        assertEquals("Chic Styles", collectionEntity.getName());
        assertEquals("This is about a Chic style", collectionEntity.getAbout());
        assertEquals(4, collectionEntity.getVisibility().getId());
        assertEquals(11L, collectionEntity.getOwner().getId());
    }

    /*@Test
    void setVisibilityInDto_public(){
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostMapper.setVisibilityInDto("public", collectionPostDto);
        assertEquals(Visibility.PUBLIC, collectionPostDto.getVisibility());
    }

    @Test
    void setVisibilityInDto_notDefined(){
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostMapper.setVisibilityInDto(null, collectionPostDto);
        assertEquals(Visibility.PUBLIC, collectionPostDto.getVisibility());
    }

    @Test
    void setVisibilityInDto_private(){
        CollectionPostDto collectionPostDto = new CollectionPostDto();
        collectionPostMapper.setVisibilityInDto("private", collectionPostDto);
        assertEquals(Visibility.PRIVATE, collectionPostDto.getVisibility());
    }

    @Test
    void toDto(){
        CollectionEntity collectionEntity = new CollectionEntity();
        collectionEntity.setId(1001);
        collectionEntity.setName("This is a test collection entity");
        collectionEntity.setAbout("THis is a test collection about");
        VisibilityEntity visibilityEntity = new VisibilityEntity();
        visibilityEntity.setVisibility("public");
        collectionEntity.setVisibility(visibilityEntity);
        UserAccountEntity userAccountEntity = new UserAccountEntity();
        userAccountEntity.setId(100000001L);
        collectionEntity.setOwner(userAccountEntity);

        CollectionPostDto collectionPostDto = collectionPostMapper.toDto(collectionEntity);

        assertEquals("1001", collectionPostDto.getCollectionId());
        assertEquals("This is a test collection entity", collectionPostDto.getName());
        assertEquals("THis is a test collection about", collectionPostDto.getAbout());
        assertEquals(Visibility.PUBLIC, collectionPostDto.getVisibility());
        assertEquals("100000001", collectionPostDto.getOwnerId());

    }*/
}