package com.example.message.controller;

import com.example.message.model.Post;
import com.example.message.model.User;
import com.example.message.repository.UserRepository;
import com.example.message.service.PostService;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * Контроллер для управления блогом администратора.
 * <p>
 * Этот контроллер предоставляет функциональность для добавления, редактирования, удаления записей блога,
 * а также для отображения панели администратора блога.
 * </p>
 */
@Controller
@RequestMapping("/blog/admin")
public class BlogAdminController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public BlogAdminController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    /**
     * Отображение панели администратора блога.
     * <p>
     * Этот метод отображает все записи блога для администратора, если у него есть соответствующая роль.
     * </p>
     *
     * @param model объект для добавления атрибутов на страницу.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @return имя представления для отображения панели администратора блога.
     */
    @GetMapping
    public String viewAdminPanel(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        model.addAttribute("posts", postService.getAllPosts()); // Добавляем список всех постов
        return "blog_admin"; // Возвращаем имя представления для панели администратора блога
    }

    /**
     * Добавление новой записи в блог.
     * <p>
     * Этот метод позволяет администратору добавить новую запись в блог, указывая заголовок, содержимое и автора.
     * </p>
     *
     * @param post объект, содержащий данные новой записи.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @param model объект для добавления атрибутов на страницу.
     * @return перенаправление на панель администратора блога с сообщением об успешном добавлении.
     */
    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }

        post.setAuthor(userService.findByUsername(userDetails.getUsername())); // Устанавливаем автора записи
        post.setPublicationDate(LocalDate.now()); // Устанавливаем дату публикации
        postService.savePost(post); // Сохраняем пост
        model.addAttribute("message", "Запись успешно добавлена"); // Добавляем сообщение об успехе
        return "redirect:/blog/admin"; // Перенаправляем на панель администратора блога
    }

    /**
     * Редактирование существующей записи в блоге.
     * <p>
     * Этот метод позволяет администратору редактировать заголовок и содержимое записи блога.
     * </p>
     *
     * @param post объект с новыми данными для записи.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @param model объект для добавления атрибутов на страницу.
     * @return перенаправление на панель администратора блога с сообщением об успешном обновлении.
     */
    @PostMapping("/edit")
    public String editPost(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        Post existingPost = postService.getPostById(post.getId()); // Получаем существующую запись по ID
        if (existingPost != null) {
            existingPost.setTitle(post.getTitle()); // Обновляем заголовок
            existingPost.setContent(post.getContent()); // Обновляем содержимое
            postService.savePost(existingPost); // Сохраняем обновленный пост
            model.addAttribute("message", "Запись успешно обновлена"); // Сообщение об успехе
        }
        return "redirect:/blog/admin"; // Перенаправляем на панель администратора блога
    }

    /**
     * Удаление записи из блога.
     * <p>
     * Этот метод позволяет администратору удалить запись из блога по ее ID.
     * </p>
     *
     * @param id ID записи, которую нужно удалить.
     * @param userDetails информация о текущем аутентифицированном пользователе.
     * @param model объект для добавления атрибутов на страницу.
     * @return перенаправление на панель администратора блога с сообщением об успешном удалении.
     */
    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        // Проверка, является ли текущий пользователь администратором
        if (userDetails == null || !hasRole(userDetails.getUsername(), "ADMIN")) {
            return "redirect:/login"; // Перенаправление на страницу логина, если нет прав
        }
        postService.deletePost(id); // Удаляем запись по ID
        model.addAttribute("message", "Запись успешно удалена"); // Сообщение об успешном удалении
        return "redirect:/blog/admin"; // Перенаправляем на панель администратора блога
    }

    /**
     * Проверка, имеет ли пользователь определенную роль.
     *
     * @param username имя пользователя.
     * @param roleName роль, которую нужно проверить.
     * @return true, если у пользователя есть указанная роль, иначе false.
     */
    private boolean hasRole(String username, String roleName) {
        User user = userService.findByUsername(username); // Получаем пользователя по имени
        return user != null && user.getRoles().stream()
                .anyMatch(role -> role.getName().equalsIgnoreCase(roleName)); // Проверяем, есть ли у пользователя нужная роль
    }
}
