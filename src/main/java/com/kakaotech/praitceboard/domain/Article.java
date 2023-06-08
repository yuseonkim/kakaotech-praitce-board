package com.kakaotech.praitceboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = { //검색할 것을 넣어준다.
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)

@Entity //테이블과 매핑할 클래스에 해당 어노테이션
public class Article extends AuditingFields{

    protected Article() {
    }

    public Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id); //
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Id//AutoIncrement를 위해서
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Setter
    @Column(nullable = false)
    private String title;  // 제목
    @Setter
    @Column(nullable = false)
    private String content;// 본문

    @Setter
    private String hashtag; // 해시태그


    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)//article 테이블로부터 온것이다 명시
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();


}
