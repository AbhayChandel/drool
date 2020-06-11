package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.data.entity.ArticleCommentEntity;
import com.hexlindia.drool.post.data.entity.ArticleEntity;
import com.hexlindia.drool.user.data.entity.UserAccountEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ArticleCommentRepositoryTest {

    @Autowired
    ArticleCommentRepository articleCommentRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    void save() {
        ArticleCommentEntity articleCommentEntity = new ArticleCommentEntity();
        articleCommentEntity.setComment("This is a dummy comment to test article comment insertion");
        UserAccountEntity userAccountEntity = entityManager.find(UserAccountEntity.class, 1L);
        articleCommentEntity.setUser(userAccountEntity);
        ArticleEntity articleEntity = entityManager.find(ArticleEntity.class, 2L);
        articleCommentEntity.setArticle(articleEntity);
        articleCommentEntity = articleCommentRepository.save(articleCommentEntity);
        assertNotNull(articleCommentEntity.getId());
    }

}