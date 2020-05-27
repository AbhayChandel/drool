package com.hexlindia.drool.post.data.repository.api;

import com.hexlindia.drool.post.data.entity.ArticleCommentEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleCommentRepository extends CrudRepository<ArticleCommentEntity, Long> {

}
