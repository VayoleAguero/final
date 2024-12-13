package com.example.message.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность "Пользователь" для управления пользователями системы.
 * <p>
 * Этот класс представляет модель данных для пользователя в системе. Каждый пользователь
 * может иметь несколько ролей и может создавать несколько постов.
 * </p>
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя, которое должно быть уникальным.
     */
    @Column(unique = true)
    private String username;

    /**
     * Хешированный пароль пользователя.
     */
    private String password;

    /**
     * Коллекция ролей, назначенных пользователю. Связь многие ко многим с сущностью {@link Role}.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonBackReference // Аннотация для разрыва циклической зависимости с Role
    private Set<Role> roles = new HashSet<>();

    /**
     * Коллекция постов, созданных пользователем. Связь один ко многим с сущностью {@link Post}.
     */
    @OneToMany(mappedBy = "author")
    @JsonBackReference // Аннотация для разрыва циклической зависимости с Post
    private Set<Post> posts = new HashSet<>();

    // Конструкторы, геттеры и сеттеры

    /**
     * Возвращает уникальный идентификатор пользователя.
     *
     * @return Идентификатор пользователя.
     */
    public Long getId() {
        return id;
    }

    /**
     * Возвращает имя пользователя.
     *
     * @return Имя пользователя.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Устанавливает имя пользователя.
     *
     * @param username Имя пользователя.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Возвращает пароль пользователя.
     *
     * @return Пароль пользователя.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Устанавливает пароль пользователя.
     *
     * @param password Пароль пользователя.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Возвращает коллекцию ролей, назначенных пользователю.
     *
     * @return Коллекция ролей пользователя.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Устанавливает коллекцию ролей для пользователя.
     *
     * @param roles Коллекция ролей.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Возвращает коллекцию постов, созданных пользователем.
     *
     * @return Коллекция постов пользователя.
     */
    public Set<Post> getPosts() {
        return posts;
    }

    /**
     * Устанавливает коллекцию постов, созданных пользователем.
     *
     * @param posts Коллекция постов.
     */
    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }
}
