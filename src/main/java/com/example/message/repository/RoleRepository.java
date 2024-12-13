package com.example.message.repository;

import com.example.message.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link Role}.
 * <p>
 * Этот интерфейс расширяет JpaRepository и предоставляет методы для выполнения операций с сущностью "Role".
 * </p>
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Находит роль по имени.
     *
     * @param name Имя роли.
     * @return Роль с указанным именем, если таковая существует, или null, если роль не найдена.
     */
    Role findByName(String name);
}
