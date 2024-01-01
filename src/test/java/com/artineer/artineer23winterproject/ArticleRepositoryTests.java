package com.artineer.artineer23winterproject;

import com.artineer.artineer23winterproject.entity.Article;
import com.artineer.artineer23winterproject.repository.ArticleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ArticleRepositoryTests {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void insertData() {

        for (int i = 0; i < 12; i++) {
            Article article = Article.builder()
                    .title("TestTitle" + i )
                    .content("TestContent" + i)
                    .localDateTime(LocalDateTime.now())
                    .build();

            articleRepository.save(article);

        }

    }

}
