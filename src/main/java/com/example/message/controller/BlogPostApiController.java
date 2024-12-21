package com.example.message.controller;

import com.example.message.model.Post;
import com.example.message.model.User;
import com.example.message.service.PostService;
import com.example.message.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     *
     * @return ResponseEntity с состоянием 200 OK и списком постов.
     */
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        try {
            List<Post> posts = postService.getAllPosts();
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Получить пост по его ID.
     *
     * @param id ID поста.
     * @return ResponseEntity с состоянием 200 OK и постом или 404 Not Found, если пост не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Создать новый пост.
     *
     * @param post данные нового поста.
     * @return ResponseEntity с состоянием 201 Created и созданным постом или 400 Bad Request, если автор не найден.
     */
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        try {
            if (post.getAuthor() == null || post.getAuthor().getId() == null) {
                return ResponseEntity.badRequest().body(null);
            }

            User author = userService.getUserById(post.getAuthor().getId());
            if (author == null) {
                return ResponseEntity.badRequest().body(null);
            }

            post.setAuthor(author);
            postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Обновить существующий пост.
     *
     * @param id   ID поста для обновления.
     * @param post обновленные данные поста.
     * @return ResponseEntity с состоянием 200 OK и обновленным постом или 404 Not Found, если пост не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post post) {
        try {
            Post existingPost = postService.getPostById(id);
            if (existingPost == null) {
                return ResponseEntity.notFound().build();
            }

            existingPost.setTitle(post.getTitle());
            existingPost.setContent(post.getContent());

            if (post.getAuthor() != null && post.getAuthor().getId() != null) {
                User author = userService.getUserById(post.getAuthor().getId());
                if (author == null) {
                    return ResponseEntity.badRequest().body(null);
                }
                existingPost.setAuthor(author);
            }

            postService.savePost(existingPost);
            return ResponseEntity.ok(existingPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Удалить пост по его ID.
     *
     * @param id ID поста для удаления.
     * @return ResponseEntity с состоянием 204 No Content, если удаление прошло успешно, или 404 Not Found, если пост не найден.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            Post post = postService.getPostById(id);
            if (post == null) {
                return ResponseEntity.notFound().build();
            }

            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
