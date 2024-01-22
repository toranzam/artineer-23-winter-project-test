package com.artineer.artineer23winterproject.controller;

import com.artineer.artineer23winterproject.auth.CurrentUser;
import com.artineer.artineer23winterproject.dto.ArticleDto;
import com.artineer.artineer23winterproject.dto.PageDto;
import com.artineer.artineer23winterproject.dto.PageResponseDto;
import com.artineer.artineer23winterproject.entity.Account;
import com.artineer.artineer23winterproject.entity.Article;
import com.artineer.artineer23winterproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ArticleController {

    private final ArticleRepository articleRepository;


//    @GetMapping("/articles")
//    public String showArticleList(Model model) {
//
//        List<Article> articles = articleRepository.findAll();
//
//        model.addAttribute("articles" ,articles);
//
//        return "article/articles";
//    }

    @GetMapping("/articles")
    public String showArticleList(Model model, PageDto pageDto) {

//        List<Article> articles = articleRepository.findAll();

        Pageable pageable = PageRequest.of(
                pageDto.getPage() -1,
                pageDto.getSize(),
                Sort.by("id").descending());

        Page<Article> articles = articleRepository.findAll(pageable);


        PageResponseDto pageResponseDto = PageResponseDto.builder()
                .pageDto(pageDto)
                .total(articleRepository.count())
                .build();



        model.addAttribute("dto" , pageResponseDto);
        model.addAttribute("articles" ,articles);

        return "article/articles";
    }

    @GetMapping("/articles/new")
    public String showNewArticleForm() {
        return "article/newArticle";

    }


    @PostMapping("/articles/new")
    public String createNewArticle(ArticleDto articleDto, @CurrentUser Account account) {

        Article article = Article.builder()
                .title(articleDto.getTitle())
                .content(articleDto.getContent())
                .localDateTime(LocalDateTime.now())
                .account(account)
                .build();

        articleRepository.save(article);

        return "redirect:/articles";
    }


    @GetMapping("/articles/{id}")
    public String showArticleDetail(@PathVariable("id") Long id,
                                    @CurrentUser Account account,
                                    Model model) {
        Optional<Article> byId = articleRepository.findById(id);

        if(byId.isEmpty()){
            throw new IllegalArgumentException("보여줄 " + id + "번 게시물이 존재하지않습니다.");
        }

        Article article = byId.get();

        model.addAttribute("article", article);
        model.addAttribute("isOwner", article.getAccount().equals(account));


        return "article/articleDetail";

    }


    @GetMapping("/articles/edit/{id}")
    public String showArticleEdit(@PathVariable("id") Long id,
                                  @CurrentUser Account account,
                                  ArticleDto articleDto,
                                  Model model
                                  ){

        Optional<Article> byId = articleRepository.findById(id);

        if(byId.isEmpty()){
            throw new IllegalArgumentException("수정할 " + id + "번 게시물이 존재하지않습니다.");
        }

        Article article = byId.get();

        if (!article.getAccount().equals(account)){
            throw new AccessDeniedException("수정할 권한이 없습니다.");
        }

        model.addAttribute("article", article);

        return "article/editArticle";
    }


    @Transactional
    @PostMapping("/articles/edit/{id}")
    public String editArticle(@PathVariable("id") Long id,
                              @CurrentUser Account account,
                              ArticleDto articleDto ){
        Optional<Article> byId = articleRepository.findById(id);

        if(byId.isEmpty()){
            throw new IllegalArgumentException("수정할 " + id + "번 게시물이 존재하지않습니다.");
        }

        Article article = byId.get();

        if (!article.getAccount().equals(account)){
            throw new AccessDeniedException("수정할 권한이 없습니다.");
        }

        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());

        return "redirect:/articles/" + article.getId();
    }

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable("id")Long id,
                                @CurrentUser Account account){
        Optional<Article> byId = articleRepository.findById(id);

        if(byId.isEmpty()){
            throw new IllegalArgumentException("삭제할 " + id + "번 게시물이 존재하지않습니다.");
        }

        Article article = byId.get();

        if (!article.getAccount().equals(account)){
            throw new AccessDeniedException("삭제할 권한이 없습니다.");
        }

        articleRepository.delete(article);

        return "redirect:/articles";

    }


}
