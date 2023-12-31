package com.artineer.artineer23winterproject.api;


import com.artineer.artineer23winterproject.entity.Article;
import com.artineer.artineer23winterproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiArticleController {

    private final ArticleRepository articleRepository;

    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> showArticleData(){
        List<Article> articleList = articleRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(articleList);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> showArticleDetail(@PathVariable("id") Long id ){
        Optional<Article> byId = articleRepository.findById(id);

        if(byId.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(byId.get());
    }

    @PostMapping("/api/articles/new")
    public ResponseEntity<Article> createArticle(@RequestParam("title") String title,
                                                @RequestParam("content") String content) {

        Article article = Article.builder()
                .title(title)
                .content(content)
                .localDateTime(LocalDateTime.now())
                .build();

        articleRepository.save(article);

        System.out.println("title = " + title + ", content = " + content);

        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }


}
