package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.entity.DiscussionEntity;
import com.hexlindia.drool.post.data.entity.PostTypeEntity;
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
class PostDtoDiscussionEntityMapperTest {

    @Autowired
    PostDtoDiscussionEntityMapper postDtoDiscussionEntityMapper;

    @MockBean
    PostTypeRepository postTypeRepositoryMock;

    @MockBean
    UserAccountRepository userAccountRepositoryMock;

    @Test
    void toEntity() {
        PostDto postDto = new PostDto();
        postDto.setId("10009021");
        postDto.setTitle("This is dummy discussion");
        postDto.setLikes("1234");
        postDto.setViews("453345");
        postDto.setText("Here explain more about the dummy discussion");
        postDto.setCoverPicture("12345_a.jps");
        postDto.setType(PostType2.DISCUSSION);
        postDto.setOwnerId("55");

        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(3);
        postTypeEntityMock.setType("discussion");
        when(this.postTypeRepositoryMock.findByType(PostType2.DISCUSSION.toString().toLowerCase())).thenReturn(Optional.of(postTypeEntityMock));
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(55L);
        when(this.userAccountRepositoryMock.findById(55L)).thenReturn(Optional.of(userAccountEntityMock));

        DiscussionEntity discussionEntity = postDtoDiscussionEntityMapper.toEntity(postDto);

        assertEquals(Long.valueOf("10009021"), discussionEntity.getId());
        assertEquals("This is dummy discussion", discussionEntity.getTitle());
        assertEquals(1234, discussionEntity.getLikes());
        assertEquals(453345, discussionEntity.getViews());
        assertEquals("Here explain more about the dummy discussion", discussionEntity.getText());
        assertEquals("12345_a.jps", discussionEntity.getCoverPicture());
        assertEquals(3, discussionEntity.getType().getId());
        assertEquals("discussion", discussionEntity.getType().getType());
        assertEquals(55L, discussionEntity.getOwner().getId());
    }

    @Test
    void toDto() {
        DiscussionEntity discussionEntity = new DiscussionEntity();
        discussionEntity.setId(154345L);
        discussionEntity.setTitle("This is discussion entity");
        discussionEntity.setLikes(12345);
        discussionEntity.setViews(5676545);
        discussionEntity.setText("This is discussion details");
        discussionEntity.setCoverPicture("123_Gzej5fj65.jpg");
        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(3);
        discussionEntity.setType(postTypeEntityMock);
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(100001L);
        discussionEntity.setOwner(userAccountEntityMock);

        PostDto postDto = postDtoDiscussionEntityMapper.toDto(discussionEntity);
        assertEquals("154345", postDto.getId());
        assertEquals("This is discussion entity", postDto.getTitle());
        assertEquals("12.3k", postDto.getLikes());
        assertEquals("5.6M", postDto.getViews());
        assertEquals("This is discussion details", postDto.getText());
        assertEquals("123_Gzej5fj65.jpg", postDto.getCoverPicture());
        assertEquals(PostType2.DISCUSSION, postDto.getType());
        assertEquals("100001", postDto.getOwnerId());

    }
}