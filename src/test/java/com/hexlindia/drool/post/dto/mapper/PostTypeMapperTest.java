package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.common.error.exception.RequestParameterNotValidException;
import com.hexlindia.drool.post.business.exception.PostTypeNotFoundException;
import com.hexlindia.drool.post.data.entity.PostTypeEntity;
import com.hexlindia.drool.post.data.repository.api.PostTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostTypeMapperTest {

    @MockBean
    PostTypeRepository postTypeRepositoryMock;

    PostTypeMapper postTypeMapperSpy;

    @BeforeEach
    void setUp() {
        postTypeMapperSpy = Mockito.spy(new PostTypeMapper(postTypeRepositoryMock));
    }

    @Test
    void enumToEntity_ArgumentNull() {
        assertThrows(RequestParameterNotValidException.class, () -> {
            postTypeMapperSpy.enumToEntity(null);
        });
    }

    @Test
    void enumToEntity_ArgumentNotNull() {
        when(this.postTypeRepositoryMock.findByType(any())).thenReturn(Optional.of(new PostTypeEntity()));
        assertNotNull(postTypeMapperSpy.enumToEntity(PostType2.ARTICLE));
    }

    @Test
    void getPostTypeEntity_NotFound() {
        when(this.postTypeRepositoryMock.findByType(any())).thenReturn(Optional.empty());
        assertThrows(PostTypeNotFoundException.class, () -> {
            postTypeMapperSpy.getPostTypeEntity(PostType2.ARTICLE);
        });
    }

    @Test
    void getPostTypeEntity_Found() {
        when(this.postTypeRepositoryMock.findByType(any())).thenReturn(Optional.of(new PostTypeEntity()));
        assertNotNull(postTypeMapperSpy.getPostTypeEntity(PostType2.ARTICLE));
    }

    @Test
    void getPostTypeEnum_Video() {
        assertEquals(PostType2.VIDEO, postTypeMapperSpy.getPostTypeEnum(1));
    }

    @Test
    void getPostTypeEnum_Article() {
        assertEquals(PostType2.ARTICLE, postTypeMapperSpy.getPostTypeEnum(2));
    }

    @Test
    void getPostTypeEnum_Discussion() {
        assertEquals(PostType2.DISCUSSION, postTypeMapperSpy.getPostTypeEnum(3));
    }

    @Test
    void getPostTypeEnum_Null() {
        assertEquals(null, postTypeMapperSpy.getPostTypeEnum(0));
    }

    @Test
    void entityToEnum_NullArgument() {
        assertNull(postTypeMapperSpy.entityToEnum(null));
    }

    @Test
    void entityToEnum() {
        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(3);
        assertEquals(PostType2.DISCUSSION, postTypeMapperSpy.entityToEnum(postTypeEntityMock));
    }


}