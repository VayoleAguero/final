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
 * <p>
 * Этот контроллер отвечает за отображение всех постов блога на главной странице и поиск постов по заголовку, содержимому
 * или дате публикации.
 * </p>
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
     * <p>
     * Этот метод загружает все посты и добавляет их в модель, а также получает информацию о количестве постов для
     * каждого пользователя.
     * </p>
     *
     * @param model объект для добавления атрибутов на страницу.
     * @return имя представления для главной страницы блога.
     */
    @GetMapping
    public String viewBlogMainPage(Model model) {
        List<Post> posts = postService.getAllPosts(); // Получаем все посты
        model.addAttribute("posts", posts); // Добавляем их в модель

        // Получаем данные о количестве постов для каждого пользователя
        List<Object[]> postsCountByUser = postService.getPostsCountByUser();
        model.addAttribute("postsCountByUser", postsCountByUser); // Добавляем в модель данные о пользователях

        return "blog_main"; // Возвращаем имя представления для главной страницы блога
    }

    /**
     * Поиск постов по заголовку, содержимому или дате.
     * <p>
     * Этот метод обрабатывает поиск по указанным параметрам: заголовок, содержание или дата публикации.
     * </p>
     *
     * @param title   заголовок для поиска.
     * @param content содержимое для поиска.
     * @param date    дата публикации для поиска.
     * @param model   объект для добавления атрибутов на страницу.
     * @return имя представления для главной страницы блога с результатами поиска.
     */
    @GetMapping("/search")
    public String searchPosts(@RequestParam(required = false) String title,
                              @RequestParam(required = false) String content,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                              Model model) {
        List<Post> posts = postService.searchPosts(title, content, date); // Ищем посты с указанными параметрами
        model.addAttribute("posts", posts); // Добавляем результаты поиска в модель
        return "blog_main"; // Возвращаем имя представления для главной страницы блога с результатами поиска
    }
}
