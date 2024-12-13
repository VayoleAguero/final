package com.example.message.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

/**
 * Сущность "Роль" для управления ролями пользователей.
 * <p>
 * Этот класс представляет модель данных для роли, которую может иметь пользователь.
 * Роль определяет доступ и права пользователя в системе.
 * </p>
 */
@Entity
@Table(name = "roles")
public class Role {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли (например, ADMIN, USER, VIEWER).
     */
    private String name;

    /**
     * Коллекция пользователей, которым назначена эта роль (связь многие ко многим с сущностью {@link User}).
     */
    @ManyToMany(mappedBy = "roles")
    @JsonIgnore // Аннотация для игнорирования циклической зависимости с User
    private Set<User> users;

    // Конструкторы, геттеры и сеттеры

    /**
     * Конструктор без параметров для Hibernate.
     */
    public Role() {}

    /**
     * Конструктор для создания новой роли с заданным названием.
     *
     * @param name Название роли.
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Возвращает уникальный идентификатор роли.
     *
     * @return Идентификатор роли.
     */
    public Long getId() {
        return id;
    }

    /**
     * Устанавливает уникальный идентификатор роли.
     *
     * @param id Идентификатор роли.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Возвращает название роли.
     *
     * @return Название роли.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название роли.
     *
     * @param name Название роли.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает коллекцию пользователей, которым назначена эта роль.
     *
     * @return Коллекция пользователей.
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Устанавливает коллекцию пользователей, которым назначена эта роль.
     *
     * @param users Коллекция пользователей.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
