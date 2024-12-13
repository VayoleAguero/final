package com.example.message.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Сущность "Пост" для хранения информации о публикации в блоге.
 * <p>
 * Этот класс представляет модель данных для поста в блоге, который содержит такие поля,
 * как заголовок, содержание, дата публикации и автор. Каждому посту соответствует один
 * автор (пользователь), с которым он ассоциирован.
 * </p>
 */
@Entity
@Table(name = "posts")
public class Post {

    /**
     * Уникальный идентификатор поста.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок поста.
     */
    private String title;

    /**
     * Дата публикации поста.
     */
    private LocalDate publicationDate;

    /**
     * Содержание поста.
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * Автор поста (связь многие к одному с сущностью {@link User}).
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference // Аннотация для разрыва циклической зависимости с User
    private User author;

    // Конструкторы

    /**
     * Конструктор без параметров для Hibernate.
     */
    public Post() {}

    /**
     * Конструктор для создания нового поста.
     *
     * @param title Заголовок поста.
     * @param content Содержание поста.
     * @param author Автор поста.
     */
    public Post(String title, String content, User author) {
        this.title = title;
        this.content = content;
        this.publicationDate = LocalDate.now();
        this.author = author;
    }

    // Геттеры и сеттеры

    /**
     * Возвращает уникальный идентификатор поста.
     *
     * @return Идентификатор поста.
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор поста.
     *
     * @param id Идентификатор поста.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает заголовок поста.
     *
     * @return Заголовок поста.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает заголовок поста.
     *
     * @param title Заголовок поста.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Возвращает дату публикации поста.
     *
     * @return Дата публикации поста.
     */
    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    /**
     * Устанавливает дату публикации поста.
     *
     * @param publicationDate Дата публикации поста.
     */
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    /**
     * Возвращает содержание поста.
     *
     * @return Содержание поста.
     */
    public String getContent() {
        return content;
    }

    /**
     * Устанавливает содержание поста.
     *
     * @param content Содержание поста.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Возвращает автора поста.
     *
     * @return Автор поста.
     */
    public User getAuthor() {
        return author;
    }

    /**
     * Устанавливает автора поста.
     *
     * @param author Автор поста.
     */
    public void setAuthor(User author) {
        this.author = author;
    }
}
