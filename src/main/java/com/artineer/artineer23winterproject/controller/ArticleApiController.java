package com.artineer.artineer23winterproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArticleApiController {


    @GetMapping("/articles/test")
    public String showArticleTestList(Model model) {
        return "articleApi/articlesApi";
    }

    @GetMapping("/articles/test/new")
    public String createArticlePage(Model model) {
        return "articleApi/newArticleApi";
    }
}
