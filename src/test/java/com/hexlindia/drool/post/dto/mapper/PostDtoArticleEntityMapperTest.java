package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.common.constant.PostType2;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
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
class PostDtoArticleEntityMapperTest {

    @Autowired
    PostDtoArticleEntityMapper postDtoArticleEntityMapper;

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
        postDto.setCoverPicture("12345_a.jps");
        postDto.setType(PostType2.ARTICLE);
        postDto.setOwnerId("55");

        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(2);
        postTypeEntityMock.setType("article");
        when(this.postTypeRepositoryMock.findByType(PostType2.ARTICLE.toString().toLowerCase())).thenReturn(Optional.of(postTypeEntityMock));
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(55L);
        when(this.userAccountRepositoryMock.findById(55L)).thenReturn(Optional.of(userAccountEntityMock));

        ArticleEntity articleEntity = postDtoArticleEntityMapper.toEntity(postDto);

        assertEquals(Long.valueOf("1000021"), articleEntity.getId());
        assertEquals("This is dummy post", articleEntity.getTitle());
        assertEquals(1234, articleEntity.getLikes());
        assertEquals(453345, articleEntity.getViews());
        assertEquals("This is dummy text for dummy post", articleEntity.getText());
        assertEquals("12345_a.jps", articleEntity.getCoverPicture());
        assertEquals(2, articleEntity.getType().getId());
        assertEquals("article", articleEntity.getType().getType());
        assertEquals(55L, articleEntity.getOwner().getId());
    }

    @Test
    void toDto() {
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(154345L);
        articleEntity.setTitle("This is a dummy article entity");
        articleEntity.setLikes(12345);
        articleEntity.setViews(5676545);
        articleEntity.setText("This is dummy text for dummy article entity");
        articleEntity.setCoverPicture("123_Gzej5fj65.jpg");
        PostTypeEntity postTypeEntityMock = new PostTypeEntity();
        postTypeEntityMock.setId(2);
        articleEntity.setType(postTypeEntityMock);
        UserAccountEntity userAccountEntityMock = new UserAccountEntity();
        userAccountEntityMock.setId(100001L);
        articleEntity.setOwner(userAccountEntityMock);

        PostDto postDto = postDtoArticleEntityMapper.toDto(articleEntity);
        assertEquals("154345", postDto.getId());
        assertEquals("This is a dummy article entity", postDto.getTitle());
        assertEquals("12.3k", postDto.getLikes());
        assertEquals("5.6M", postDto.getViews());
        assertEquals("This is dummy text for dummy article entity", postDto.getText());
        assertEquals("123_Gzej5fj65.jpg", postDto.getCoverPicture());
        assertEquals(PostType2.ARTICLE, postDto.getType());
        assertEquals("100001", postDto.getOwnerId());

    }
}