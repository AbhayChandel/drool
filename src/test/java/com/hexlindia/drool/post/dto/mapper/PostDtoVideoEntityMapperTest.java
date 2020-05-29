package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.entity.PostTypeEntity;
import com.hexlindia.drool.post.data.entity.VideoEntity;
import com.hexlindia.drool.post.data.repository.api.PostTypeRepository;
import com.hexlindia.drool.post.dto.PostDto;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import com.hexlindia.drool.user.data.repository.api.UserAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostDtoVideoEntityMapperTest {

    @Autowired
    PostDtoVideoEntityMapper postDtoVideoEntityMapper;

    @MockBean
    PostTypeRepository postTypeRepositoryMock;

    @MockBean
    UserAccountRepository userAccountRepositoryMock;

    @Test
    void toEntity() {
        PostDto postDto = new PostDto();
        postDto.setId("1000021");
        postDto.setTitle("This is dummy post");
        postDto.setLikes("1234");
        postDto.setViews("453345");
        postDto.setText("This is dummy text for dummy post");
        postDto.setSourceVideoId("xTsdY78");
        postDto.setType(PostType2.VIDEO);
        postDto.setOwnerId("77");

        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(1);
        postTypeEntityMock.setType("video");
        when(this.postTypeRepositoryMock.findByType(PostType2.VIDEO.toString().toLowerCase())).thenReturn(Optional.of(postTypeEntityMock));
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(77L);
        when(this.userAccountRepositoryMock.findById(77L)).thenReturn(Optional.of(userAccountEntityMock));

        VideoEntity videoEntity = postDtoVideoEntityMapper.toEntity(postDto);

        assertEquals(Long.valueOf("1000021"), videoEntity.getId());
        assertEquals("This is dummy post", videoEntity.getTitle());
        assertEquals(1234, videoEntity.getLikes());
        assertEquals(453345, videoEntity.getViews());
        assertEquals("This is dummy text for dummy post", videoEntity.getText());
        assertEquals("xTsdY78", videoEntity.getSourceVideoId());
        assertEquals(1, videoEntity.getType().getId());
        assertEquals("video", videoEntity.getType().getType());
        assertEquals(77L, videoEntity.getOwner().getId());
    }

    @Test
    void toDto() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(154345L);
        videoEntity.setTitle("This is a dummy video entity");
        videoEntity.setLikes(45876);
        videoEntity.setViews(8976545);
        videoEntity.setText("This is dummy text for dummy video entity");
        videoEntity.setSourceVideoId("dgnd67");
        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(1);
        videoEntity.setType(postTypeEntityMock);
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(1111L);
        videoEntity.setOwner(userAccountEntityMock);

        PostDto postDto = postDtoVideoEntityMapper.toDto(videoEntity);

        assertEquals("154345", postDto.getId());
        assertEquals("This is a dummy video entity", postDto.getTitle());
        assertEquals("45.8k", postDto.getLikes());
        assertEquals("8.9M", postDto.getViews());
        assertEquals("This is dummy text for dummy video entity", postDto.getText());
        assertEquals("dgnd67", postDto.getSourceVideoId());
        assertEquals(PostType2.VIDEO, postDto.getType());
        assertEquals("1111", postDto.getOwnerId());
    }
}