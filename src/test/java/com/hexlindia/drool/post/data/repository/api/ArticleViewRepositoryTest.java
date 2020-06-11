package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.dto.PostDto;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ArticleViewRepositoryTest {


    ArticleViewRepository articleViewRepository;


    void getPost() {
        PostDto postDto = articleViewRepository.getArticle(2L);
        assertNotNull(postDto);
    }
}