package com.artineer.artineer23winterproject.api;


import com.artineer.artineer23winterproject.auth.CurrentUser;
import com.artineer.artineer23winterproject.dto.article.ArticleRequestDto;
import com.artineer.artineer23winterproject.dto.article.ArticleResponseDto;
import com.artineer.artineer23winterproject.entity.Account;
import com.artineer.artineer23winterproject.entity.Article;
import com.artineer.artineer23winterproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor

public class ArticleApiController {

    private final ArticleRepository articleRepository;

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponseDto>> showArticleData() {

        List<Article> articleList = articleRepository.findAll();

        List<ArticleResponseDto> collect = articleList.stream()
                .map(m -> ArticleResponseDto.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .content(m.getContent())
                        .localDateTime(m.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                        .author(m.getAccount().getUsername())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(collect);
    }

    @GetMapping("/api/articles/{id}")
    public ResponseEntity<Article> showArticleDetail(@PathVariable("id") Long id) {
        Optional<Article> byId = articleRepository.findById(id);

        if (byId.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(byId.get());
    }

//    @PostMapping("/api/articles/new")
//    public ResponseEntity<Article> createArticle(@RequestParam("title") String title,
//                                                @RequestParam("content") String content) {
//
//        Article article = Article.builder()
//                .title(title)
//                .content(content)
//                .localDateTime(LocalDateTime.now())
//                .build();
//
//        articleRepository.save(article);
//
//        System.out.println("title = " + title + ", content = " + content);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(article);
//
//    }

    @PostMapping("/api/articles/new")
    public ResponseEntity<String> createArticle(@RequestBody ArticleRequestDto articleRequestDto,
                                                 @CurrentUser Account account) {

        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .localDateTime(LocalDateTime.now())
                .account(account)
                .build();

        Article saved = articleRepository.save(article);

        System.out.println("title = " + articleRequestDto.getTitle() + ", content = " + articleRequestDto.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(String.valueOf((saved.getId())));

    }


}
