package com.example.message.service;

import com.example.message.model.Post;
import com.example.message.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Сервис для управления постами.
 * <p>
 * Этот сервис предоставляет методы для работы с сущностью {@link Post}. Он включает операции
 * для получения всех постов, сохранения нового поста, поиска постов по различным критериям,
 * удаления постов и получения статистики по количеству постов для каждого пользователя.
 * </p>
 */
@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    /**
     * Получает все посты.
     *
     * @return Список всех постов.
     */
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Сохраняет новый пост.
     *
     * @param post Объект поста, который будет сохранен.
     */
    public void savePost(Post post) {
        postRepository.save(post);
    }

    /**
     * Получает пост по его идентификатору.
     *
     * @param id Идентификатор поста.
     * @return Пост с заданным идентификатором или null, если не найден.
     */
    public Post getPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * Удаляет пост по его идентификатору.
     *
     * @param id Идентификатор поста, который нужно удалить.
     */
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    /**
     * Ищет посты по заданным параметрам (название, содержание, дата публикации).
     *
     * @param title Заголовок поста.
     * @param content Содержание поста.
     * @param date Дата публикации поста.
     * @return Список постов, удовлетворяющих хотя бы одному из условий.
     */
    public List<Post> searchPosts(String title, String content, LocalDate date) {
        if (title != null && !title.isEmpty() && content == null && date == null) {
            return postRepository.findByTitleContainingIgnoreCase(title);
        } else if (title == null && content != null && !content.isEmpty() && date == null) {
            return postRepository.findByContentContainingIgnoreCase(content);
        } else if (title == null && content == null && date != null) {
            return postRepository.findByPublicationDate(date);
        } else if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && date == null) {
            return postRepository.findByTitleContainingIgnoreCaseAndContentContainingIgnoreCase(title, content);
        } else if (title != null && !title.isEmpty() && content == null && date != null) {
            return postRepository.findByTitleContainingIgnoreCaseAndPublicationDate(title, date);
        } else if (title == null && content != null && !content.isEmpty() && date != null) {
            return postRepository.findByContentContainingIgnoreCaseAndPublicationDate(content, date);
        } else if (title != null && !title.isEmpty() && content != null && !content.isEmpty() && date != null) {
            return postRepository.findByTitleContainingIgnoreCaseAndContentContainingIgnoreCaseAndPublicationDate(title, content, date);
        } else {
            return postRepository.findAll();
        }
    }

    /**
     * Получает количество постов для каждого пользователя.
     *
     * @return Список объектов, где каждый объект содержит информацию о пользователе и количестве его постов.
     */
    public List<Object[]> getPostsCountByUser() {
        return postRepository.countPostsByUser();
    }
}
