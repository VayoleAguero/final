package com.example.message.controller;

import com.example.message.model.Post;
import com.example.message.model.User;
import com.example.message.service.PostService;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Контроллер для работы с API постов блога.
 * <p>
 * Этот контроллер предоставляет REST API для создания, получения, обновления и удаления постов блога.
 * </p>
 */
@RestController
@RequestMapping("/api/posts")
public class BlogPostApiController {

    private final PostService postService;
    private final UserService userService;

    @Autowired
    public BlogPostApiController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    /**
     * Получить все посты блога.
     * <p>
     * Этот метод возвращает список всех постов в формате JSON.
     * </p>
     *
     * @return ResponseEntity с состоянием 200 OK и списком постов.
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts); // Возвращаем 200 OK с данными постов
    }

    /**
     * Получить пост по его ID.
     * <p>
     * Этот метод возвращает конкретный пост по ID или 404 Not Found, если пост с таким ID не существует.
     * </p>
     *
     * @param id ID поста.
     * @return ResponseEntity с состоянием 200 OK и постом или 404 Not Found, если пост не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build(); // Если пост не найден, возвращаем 404 Not Found
        }
        return ResponseEntity.ok(post); // Возвращаем 200 OK с найденным постом
    }

    /**
     * Создать новый пост.
     * <p>
     * Этот метод создает новый пост в блоге, проверяя, что автор существует, и возвращает созданный пост.
     * </p>
     *
     * @param post данные нового поста.
     * @return ResponseEntity с состоянием 201 Created и созданным постом или 400 Bad Request, если автор не найден.
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        // Убедимся, что автор существует
        User author = userService.getUserById(post.getAuthor().getId());
        if (author == null) {
            return ResponseEntity.badRequest().body(null); // Возвращаем ошибку, если автор не найден
        }
        post.setAuthor(author); // Устанавливаем автором найденного пользователя
        postService.savePost(post); // Сохраняем пост
        return ResponseEntity.status(HttpStatus.CREATED).body(post); // Возвращаем 201 Created с созданным постом
    }

    /**
     * Обновить существующий пост.
     * <p>
     * Этот метод обновляет данные поста по ID и возвращает обновленный пост.
     * Если пост не найден, возвращает 404 Not Found.
     * </p>
     *
     * @param id   ID поста для обновления.
     * @param post обновленные данные поста.
     * @return ResponseEntity с состоянием 200 OK и обновленным постом или 404 Not Found, если пост не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        // Проверим, существует ли пост с таким ID
        Post existingPost = postService.getPostById(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build(); // Если пост не найден, возвращаем 404
        }

        // Обновим данные поста
        existingPost.setTitle(post.getTitle());
        existingPost.setContent(post.getContent());

        // Если автор был изменен, проверим его существование
        if (post.getAuthor() != null && post.getAuthor().getId() != null) {
            User author = userService.getUserById(post.getAuthor().getId());
            if (author == null) {
                return ResponseEntity.badRequest().body(null); // Если автор не найден, возвращаем ошибку
            }
            existingPost.setAuthor(author);
        }

        // Сохраняем обновленный пост
        postService.savePost(existingPost);

        return ResponseEntity.ok(existingPost); // Возвращаем 200 OK с обновленным постом
    }

    /**
     * Удалить пост по его ID.
     * <p>
     * Этот метод удаляет пост по ID. Если пост не найден, возвращает 404 Not Found.
     * </p>
     *
     * @param id ID поста для удаления.
     * @return ResponseEntity с состоянием 204 No Content, если удаление прошло успешно, или 404 Not Found, если пост не найден.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        // Проверим, существует ли пост с таким ID
        Post post = postService.getPostById(id);
        if (post == null) {
            return ResponseEntity.notFound().build(); // Если пост не найден, возвращаем 404
        }

        // Удалим пост
        postService.deletePost(id);

        return ResponseEntity.noContent().build(); // Возвращаем статус 204 No Content (удаление прошло успешно)
    }
}
