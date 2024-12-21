package com.example.message.controller;

import com.example.message.model.Post;
import com.example.message.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для отображения главной страницы блога и поиска постов.
 */
@Controller
@RequestMapping("/blog")
public class BlogController {

    private final PostService postService;

    @Autowired
    public BlogController(PostService postService) {
        this.postService = postService;
    }

    /**
     * Отображение главной страницы блога с перечнем всех постов и количеством постов для каждого пользователя.
     */
    @GetMapping
    public String viewBlogMainPage(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);

        // Получаем статистику постов по пользователям
        List<Object[]> postsCountByUser = postService.getPostsCountByUser();

        // Формируем массив для гистограммы
        String dataForChart = postsCountByUser.stream()
                .map(obj -> String.format("[\"%s\", %d]", obj[0].toString(), ((Number) obj[1]).intValue()))
                .reduce((a, b) -> a + "," + b)
                .map(s -> "[" + s + "]")
                .orElse("[]");

        model.addAttribute("postsCountByUser", dataForChart);

        return "blog_main";
    }

    /**
     * Поиск постов по заголовку, содержимому или дате.
     */
    @GetMapping("/search")
    public String searchPosts(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String content,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              Model model) {
        List<Post> posts = postService.searchPosts(title, content, date);
        model.addAttribute("posts", posts);
        return "blog_main";
    }
}
