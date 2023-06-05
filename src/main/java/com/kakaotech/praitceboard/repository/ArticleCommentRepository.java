package com.kakaotech.praitceboard.repository;

import com.kakaotech.praitceboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCommentRepository extends JpaRepository<ArticleComment,Long>  {
}
