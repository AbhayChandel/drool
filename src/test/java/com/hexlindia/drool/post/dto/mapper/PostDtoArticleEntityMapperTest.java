package com.hexlindia.drool.post.dto.mapper;

import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.post.dto.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PostDtoArticleEntityMapperTest {

    @Autowired
    PostDtoArticleEntityMapper postDtoArticleEntityMapper;

    @Test
    void toEntity() {
        PostDto postDto = new PostDto();
        postDto.setId("1000021");
        postDto.setTitle("This is dummy post");
        postDto.setLikes("1234");
        postDto.setViews("453345");
        postDto.setText("This is dummy text for dummy post");
        postDto.setCoverPicture("12345_a.jps");

        ArticleEntity articleEntity = postDtoArticleEntityMapper.toEntity(postDto);

        assertEquals(Long.valueOf("1000021"), articleEntity.getId());
        assertEquals("This is dummy post", articleEntity.getTitle());
        assertEquals(1234, articleEntity.getLikes());
        assertEquals(453345, articleEntity.getViews());
        assertEquals("This is dummy text for dummy post", articleEntity.getText());
        assertEquals("12345_a.jps", articleEntity.getCoverPicture());

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

        PostDto postDto = postDtoArticleEntityMapper.toDto(articleEntity);
        assertEquals("154345", postDto.getId());
        assertEquals("This is a dummy article entity", postDto.getTitle());
        assertEquals("12345", postDto.getLikes());
        assertEquals("5676545", postDto.getViews());
        assertEquals("This is dummy text for dummy article entity", postDto.getText());
        assertEquals("123_Gzej5fj65.jpg", postDto.getCoverPicture());

    }
}