package com.example.message.repository;

import com.example.message.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с сущностью {@link User}.
 * <p>
 * Этот интерфейс расширяет JpaRepository и предоставляет методы для выполнения операций с сущностью "User".
 * </p>
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username Имя пользователя.
     * @return Пользователь с указанным именем, если таковой существует, или null, если пользователь не найден.
     */
    User findByUsername(String username);
}
