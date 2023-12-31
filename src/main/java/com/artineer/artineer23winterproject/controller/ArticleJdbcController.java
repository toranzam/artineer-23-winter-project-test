package com.artineer.artineer23winterproject.controller;

import com.artineer.artineer23winterproject.dto.ArticleDto;
import com.artineer.artineer23winterproject.entity.ArticleJdbc;
import com.artineer.artineer23winterproject.repository.ArticleJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/jdbc")
public class ArticleJdbcController {

    private final ArticleJdbcRepository articleJdbcRepository;

    @GetMapping("/articles")
    public String showArticleList(Model model) {

        List<ArticleJdbc> articleJdbcList = articleJdbcRepository.findAll();

        model.addAttribute("articles", articleJdbcList);

        return "articleJdbc/articles";
    }


    @GetMapping("/articles/new")
    public String showNewArticleForm() {

        return "articleJdbc/newArticle";

    }


    @PostMapping("/articles/new")
    public String createNewArticle(ArticleDto articleDto) {

        ArticleJdbc articleJdbc = ArticleJdbc.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .localDateTime(LocalDateTime.now())
                .build();

        articleJdbcRepository.save(articleJdbc);

        return "redirect:/jdbc/articles";
    }


    @GetMapping("/articles/{id}")
    public String showArticleDetail(@PathVariable("id") Long id, Model model) {
//        Optional<Article> byId = articleJdbcRepository.findById(id);
        Optional<ArticleJdbc> byId = articleJdbcRepository.findById(id);

        if (byId.isEmpty()) {
            throw new IllegalArgumentException("보여줄 " + id + "번 게시물이 존재하지않습니다.");
        }

        ArticleJdbc articleJdbc = byId.get();

        model.addAttribute("article", articleJdbc);

        return "articleJdbc/articleDetail";

    }


    @GetMapping("/articles/edit/{id}")
    public String showArticleEdit(@PathVariable("id") Long id, ArticleDto articleDto, Model model) {
        Optional<ArticleJdbc> byId = articleJdbcRepository.findById(id);

        if (byId.isEmpty()) {
            throw new IllegalArgumentException("수정할 " + id + "번 게시물이 존재하지않습니다.");
        }

        ArticleJdbc article = byId.get();

        model.addAttribute("article", article);

        return "articleJdbc/editArticle";
    }


    @PostMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable("id") Long id, ArticleDto articleDto) {
        Optional<ArticleJdbc> byId = articleJdbcRepository.findById(id);

        if (byId.isEmpty()) {
            throw new IllegalArgumentException("수정할 " + id + "번 게시물이 존재하지않습니다.");
        }

        ArticleJdbc article = byId.get();

        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());

        articleJdbcRepository.update(article);

        return "redirect:/jdbc/articles/" + article.getId();
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable("id") Long id) {
        Optional<ArticleJdbc> byId = articleJdbcRepository.findById(id);

        if (byId.isEmpty()) {
            throw new IllegalArgumentException("삭제할 " + id + "번 게시물이 존재하지않습니다.");
        }

        ArticleJdbc article = byId.get();

        articleJdbcRepository.delete(article);

        return "redirect:/jdbc/articles";

    }


}
