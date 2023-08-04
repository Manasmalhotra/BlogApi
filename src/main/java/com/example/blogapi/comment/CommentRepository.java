package com.example.blogapi.comment;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {
   List<CommentEntity> findByArticleId(long articleId);
}
