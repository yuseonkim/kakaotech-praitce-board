package com.kakaotech.praitceboard.repository;

import com.kakaotech.praitceboard.config.JpaConfig;
import com.kakaotech.praitceboard.domain.Article;
import com.kakaotech.praitceboard.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("JPA 연결 테스트")
@DataJpaTest
@Import(JpaConfig.class)
class JPARepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JPARepositoryTest(@Autowired ArticleRepository articleRepository,
                             @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine(){
        //Given

        //When
        List<Article> articles = articleRepository.findAll();

        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(1);

    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_WhenInserting_thenWorksFine(){
        //Given
        long previousCount = articleRepository.count();
        Article article = Article.of("new Article","new content","#spring");
        Article savedArticle = articleRepository.save(article);

        //when
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);

    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatingHashtag = "#springboot";
        article.setHashtag(updatingHashtag);

        //When
        Article savedArticle = articleRepository.save(article);
        //Then

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag",updatingHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine(){
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        articleRepository.delete(article);


        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }
}