package com.kakaotech.praitceboard.repository;

import com.kakaotech.praitceboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}