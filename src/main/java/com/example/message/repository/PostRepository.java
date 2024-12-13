package com.example.message.repository;

import com.example.message.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Post}.
 * <p>
 * Этот интерфейс расширяет JpaRepository и предоставляет методы для выполнения различных операций с сущностью "Post",
 * таких как поиск постов по заголовку, содержимому или дате публикации, а также подсчёт количества постов по каждому пользователю.
 * </p>
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Находит посты, в которых заголовок содержит указанный текст (без учёта регистра).
     *
     * @param title Текст для поиска в заголовке.
     * @return Список постов, содержащих указанный текст в заголовке.
     */
    List<Post> findByTitleContainingIgnoreCase(String title);

    /**
     * Находит посты, в которых содержимое содержит указанный текст (без учёта регистра).
     *
     * @param content Текст для поиска в содержимом поста.
     * @return Список постов, содержащих указанный текст в содержимом.
     */
    List<Post> findByContentContainingIgnoreCase(String content);

    /**
     * Находит посты, опубликованные в указанную дату.
     *
     * @param publicationDate Дата публикации.
     * @return Список постов, опубликованных в указанную дату.
     */
    List<Post> findByPublicationDate(LocalDate publicationDate);

    /**
     * Находит посты, заголовок которых содержит указанный текст и которые опубликованы в указанную дату.
     *
     * @param title          Текст для поиска в заголовке.
     * @param publicationDate Дата публикации.
     * @return Список постов, соответствующих критериям.
     */
    List<Post> findByTitleContainingIgnoreCaseAndPublicationDate(String title, LocalDate publicationDate);

    /**
     * Находит посты, содержимое которых содержит указанный текст и которые опубликованы в указанную дату.
     *
     * @param content        Текст для поиска в содержимом.
     * @param publicationDate Дата публикации.
     * @return Список постов, соответствующих критериям.
     */
    List<Post> findByContentContainingIgnoreCaseAndPublicationDate(String content, LocalDate publicationDate);

    /**
     * Находит посты, заголовок которых содержит указанный текст и содержимое которых содержит указанный текст.
     *
     * @param title   Текст для поиска в заголовке.
     * @param content Текст для поиска в содержимом.
     * @return Список постов, соответствующих критериям.
     */
    List<Post> findByTitleContainingIgnoreCaseAndContentContainingIgnoreCase(String title, String content);

    /**
     * Находит посты, которые соответствуют всем трем критериям: заголовок, содержимое и дата публикации.
     *
     * @param title   Текст для поиска в заголовке.
     * @param content Текст для поиска в содержимом.
     * @param publicationDate Дата публикации.
     * @return Список постов, соответствующих критериям.
     */
    List<Post> findByTitleContainingIgnoreCaseAndContentContainingIgnoreCaseAndPublicationDate(String title, String content, LocalDate publicationDate);

    /**
     * Подсчитывает количество постов для каждого пользователя (автора).
     * <p>
     * Этот метод возвращает список массивов объектов, где каждый элемент массива представляет собой пару
     * (пользователь, количество его постов).
     * </p>
     *
     * @return Список с подсчётом постов для каждого автора.
     */
    @Query("SELECT p.author, COUNT(p) FROM Post p GROUP BY p.author")
    List<Object[]> countPostsByUser();
}
