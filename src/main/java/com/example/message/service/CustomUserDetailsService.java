package com.example.message.service;

import com.example.message.model.User;
import com.example.message.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Сервис для получения деталей пользователя для аутентификации в Spring Security.
 * <p>
 * Этот сервис реализует интерфейс {@link UserDetailsService}, предоставляя способ загрузки
 * данных пользователя на основе имени пользователя.
 * </p>
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Конструктор для инъекции зависимости {@link UserRepository}.
     *
     * @param userRepository Репозиторий для работы с пользователями.
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает пользователя по имени пользователя для аутентификации.
     *
     * @param username Имя пользователя.
     * @return Детали пользователя для Spring Security.
     * @throws UsernameNotFoundException Если пользователь с таким именем не найден.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Возвращаем объект UserDetails, который содержит данные пользователя для аутентификации.
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Зашифрованный пароль
                .roles(user.getRoles().stream().map(role -> role.getName()).toArray(String[]::new)) // Роли пользователя
                .build();
    }
}
